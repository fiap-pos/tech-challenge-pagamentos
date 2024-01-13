package br.com.fiap.techchallenge.pagamentos.adapters.repository;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.sqs.CobrancaSqsPublisher;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.mapper.CobrancaMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.out.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaCobrancaOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
@Repository
public class CobrancaRepository implements CriaCobrancaOutputPort, BuscaCobrancaOutputPort,
        AtualizaStatusCobrancaOutputPort {

    private final CobrancaJpaRepository cobrancaJpaRepository;
    private final CobrancaMapper cobrancaMapper;
    private final CobrancaSqsPublisher cobrancaSqsPublisher;

    public CobrancaRepository(CobrancaJpaRepository cobrancaJpaRepository, CobrancaMapper cobrancaMapper, CobrancaSqsPublisher cobrancaSqsPublisher) {
        this.cobrancaJpaRepository = cobrancaJpaRepository;
        this.cobrancaMapper = cobrancaMapper;
        this.cobrancaSqsPublisher = cobrancaSqsPublisher;

    }
    @Override
    public CobrancaDTO criar(CobrancaDTO cobrancaDTO) {
        var cobranca = cobrancaMapper.toCobranca(cobrancaDTO);
        var cobrancaSalva = cobrancaJpaRepository.save(cobranca);
        return cobrancaMapper.toCobrancaOut(cobrancaSalva);
    }

    @Override
    public CobrancaDTO buscarPorId(Long id) {
        var cobranca = buscaCobrancaPorId(id);
        return cobrancaMapper.toCobrancaOut(cobranca);
    }

    @Override
    public CobrancaDTO buscarPorPedidoId(Long pedidoId) {
        var cobranca = cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Cobrança com o pedidoId " + pedidoId + " não existe"));
        return cobrancaMapper.toCobrancaOut(cobranca);
    }

    @Override
    public boolean pedidoPossuiCobranca(Long pedidoId) {
        return cobrancaJpaRepository.existsCobrancaByPedidoId(pedidoId);
    }


    @Override
    public CobrancaDTO atualizarStatus(Long id, StatusEnum status) throws JsonProcessingException {
        var cobranca = buscaCobrancaPorId(id);
        cobranca.setStatus(status);
        var cobrancaSalva = cobrancaJpaRepository.save(cobranca);
        var cobrancaDTO = cobrancaMapper.toCobrancaOut(cobrancaSalva);
        cobrancaSqsPublisher.enviarParaFilaPagamentos(cobrancaDTO);
        return cobrancaDTO;
    }

    private Cobranca buscaCobrancaPorId(Long id) {
        return cobrancaJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cobrança com o id " + id + " não existe"));
    }
}
