package br.com.fiap.techchallenge.pagamentos.core.port.in;

import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;

public interface BuscaStatusPagamentoInputPort {
    StatusPagamentoDTO buscaStatus(Long id);
}
