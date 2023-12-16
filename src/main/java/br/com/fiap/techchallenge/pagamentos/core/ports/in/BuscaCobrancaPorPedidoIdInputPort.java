package br.com.fiap.techchallenge.pagamentos.core.ports.in;


import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;

public interface BuscaCobrancaPorPedidoIdInputPort {
    CobrancaDTO buscarPorPedidoId(Long pedidoId);
}
