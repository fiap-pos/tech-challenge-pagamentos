package br.com.fiap.techchallenge.pagamentos.core.port.in;


import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;

public interface BuscaCobrancaPorPedidoIdInputPort {
    CobrancaDTO buscarPorPedidoId(Long pedidoId);
}
