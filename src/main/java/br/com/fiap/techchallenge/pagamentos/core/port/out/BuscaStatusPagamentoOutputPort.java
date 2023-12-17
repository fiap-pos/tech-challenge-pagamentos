package br.com.fiap.techchallenge.pagamentos.core.port.out;

import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;

public interface BuscaStatusPagamentoOutputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}

