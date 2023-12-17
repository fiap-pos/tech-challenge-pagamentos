package br.com.fiap.techchallenge.pagamentos.core.port.out;

import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;

public interface BuscarPedidoPorIdOutputPort {
    PedidoDTO buscarPorId(Long id);
}