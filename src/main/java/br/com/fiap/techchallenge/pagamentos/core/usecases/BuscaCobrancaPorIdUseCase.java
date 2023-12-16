package br.com.fiap.techchallenge.pagamentos.core.usecases;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.ports.in.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.pagamentos.core.ports.out.BuscaCobrancaOutputPort;

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
