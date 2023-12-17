package br.com.fiap.techchallenge.pagamentos.core.usecase;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;

public class BuscaCobrancaPorIdUseCase implements BuscaCobrancaPorIdInputPort {

    private BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    public BuscaCobrancaPorIdUseCase(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
    }
    @Override
    public CobrancaDTO buscarPorId(Long id) {
        return buscaCobrancaOutputPort.buscarPorId(id);
    }

}
