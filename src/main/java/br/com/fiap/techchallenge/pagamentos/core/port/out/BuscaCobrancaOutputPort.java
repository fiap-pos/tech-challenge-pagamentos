package br.com.fiap.techchallenge.pagamentos.core.port.out;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;

public interface BuscaCobrancaOutputPort {
    CobrancaDTO buscarPorId(Long id);

    CobrancaDTO buscarPorPedidoId(Long pedidoId);

    boolean pedidoPossuiCobranca(Long pedidoId);
}

