package br.com.fiap.techchallenge.pagamentos.core.usecase;

import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaStatusPagamentoInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaStatusPagamentoOutputPort;

public class BuscaStatusPagamentoUseCase implements BuscaStatusPagamentoInputPort {

    private final BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort;

    public BuscaStatusPagamentoUseCase(BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort) {
        this.buscaStatusPagamentoOutputPort = buscaStatusPagamentoOutputPort;
    }

    @Override
    public StatusPagamentoDTO buscaStatus(Long id) {
        return buscaStatusPagamentoOutputPort.buscaStatus(id);
    }
}

