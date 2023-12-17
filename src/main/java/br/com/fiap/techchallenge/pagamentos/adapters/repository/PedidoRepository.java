package br.com.fiap.techchallenge.pagamentos.adapters.repository;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa.PedidoJpaRepository;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.mapper.PedidoMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Pedido;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.out.AtualizaStatusPedidoOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscarPedidoPorIdOutputPort;
import org.springframework.stereotype.Repository;

@Repository
public class PedidoRepository implements BuscarPedidoPorIdOutputPort, AtualizaStatusPedidoOutputPort {
    private final PedidoMapper pedidoMapper;
    private final PedidoJpaRepository pedidoJpaRepository;

    public PedidoRepository(PedidoMapper pedidoMapper, PedidoJpaRepository pedidoJpaRepository) {
        this.pedidoMapper = pedidoMapper;
        this.pedidoJpaRepository = pedidoJpaRepository;
    }

    @Override
    public PedidoDTO buscarPorId(Long id) {
        var pedidoBuscado = buscarPedidoPorId(id);
        return pedidoMapper.toPedidoDTO(pedidoBuscado);
    }

    private Pedido buscarPedidoPorId(Long id){
        return pedidoJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido " + id + " não encontrado"));
    }

    @Override
    public PedidoDTO atualizarStatus(Long id, StatusPedidoEnum status) {
        var pedidoBuscado = buscarPedidoPorId(id);
        pedidoBuscado.setStatus(status);
        var pedido = pedidoJpaRepository.save(pedidoBuscado);
        return pedidoMapper.toPedidoDTO(pedido);
    }
}