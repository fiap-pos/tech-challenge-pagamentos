package br.com.fiap.techchallenge.pagamentos.core.port.in;

import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;

public interface BuscarPedidoPorIdInputPort {
    PedidoDTO buscarPorId(Long id);
}

