package br.com.fiap.techchallenge.pagamentos.core.ports.in;

import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;

public interface BuscaStatusPagamentoInputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}
