package br.com.fiap.techchallenge.pagamentos.core.usecase;

import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscarPedidoPorIdInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscarPedidoPorIdOutputPort;

public class BuscarPedidoPorIdUseCase implements BuscarPedidoPorIdInputPort {
    private final BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort;
    public BuscarPedidoPorIdUseCase(BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort) {
        this.buscarPedidoPorIdOutputPort = buscarPedidoPorIdOutputPort;
    }
    @Override
    public PedidoDTO buscarPorId(Long id) {
        return buscarPedidoPorIdOutputPort.buscarPorId(id);
    }
}