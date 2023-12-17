package br.com.fiap.techchallenge.pagamentos.adapters.repository;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.mapper.CobrancaMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import br.com.fiap.techchallenge.pagamentos.core.dto.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.out.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaCobrancaOutputPort;
import org.springframework.stereotype.Repository;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
@Repository
public class CobrancaRepository implements CriaCobrancaOutputPort, BuscaCobrancaOutputPort,
        AtualizaStatusCobrancaOutputPort {

    private final CobrancaJpaRepository cobrancaJpaRepository;
    private final CobrancaMapper cobrancaMapper;

    public CobrancaRepository(CobrancaJpaRepository cobrancaJpaRepository, CobrancaMapper cobrancaMapper) {
        this.cobrancaJpaRepository = cobrancaJpaRepository;
        this.cobrancaMapper = cobrancaMapper;
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
    public CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn) {
        var cobranca = buscaCobrancaPorId(id);
        cobranca.setStatus(cobrancaStatusIn.status());
        var cobrancaSalva = cobrancaJpaRepository.save(cobranca);
        return cobrancaMapper.toCobrancaOut(cobrancaSalva);
    }

    private Cobranca buscaCobrancaPorId(Long id) {
        return cobrancaJpaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cobrança com o id " + id + " não existe"));
    }
}
