package br.com.fiap.techchallenge.pagamentos.core.ports.in;

import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;

public interface BuscarPedidoPorIdInputPort {
    PedidoDTO buscarPorId(Long id);
}

