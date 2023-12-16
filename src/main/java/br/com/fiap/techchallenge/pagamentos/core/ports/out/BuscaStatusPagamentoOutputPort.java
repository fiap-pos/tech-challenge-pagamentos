package br.com.fiap.techchallenge.pagamentos.core.ports.out;

import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;

public interface BuscaStatusPagamentoOutputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}

