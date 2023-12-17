package br.com.fiap.techchallenge.pagamentos.core.usecase;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaCobrancaPorPedidoIdInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;

public class BuscaCobrancaPorPedidoIdUseCase implements BuscaCobrancaPorPedidoIdInputPort {

    private BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    public BuscaCobrancaPorPedidoIdUseCase(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
    }
    @Override
    public CobrancaDTO buscarPorPedidoId(Long pedidoId) { return buscaCobrancaOutputPort.buscarPorPedidoId(pedidoId); }
}

