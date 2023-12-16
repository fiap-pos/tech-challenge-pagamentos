package br.com.fiap.techchallenge.pagamentos.core.usecases;


import br.com.fiap.techchallenge.pagamentos.core.domain.entities.Cobranca;
import br.com.fiap.techchallenge.pagamentos.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.pagamentos.core.domain.exceptions.EntityAlreadyExistException;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.ports.in.CriaCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.ports.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.ports.out.BuscarPedidoPorIdOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.ports.out.CriaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.ports.out.CriaQrCodeOutputPort;

public class CriaCobrancaUseCase implements CriaCobrancaInputPort {

    private final CriaCobrancaOutputPort cobrancaOutputPort;

    private final CriaQrCodeOutputPort criaQrCodeOutputPort;

    private final BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort;

    private final BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    public CriaCobrancaUseCase(
            CriaCobrancaOutputPort cobrancaOutputPort,
            CriaQrCodeOutputPort criaQrCodeOutputPort,
            BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort
    ) {
        this.cobrancaOutputPort = cobrancaOutputPort;
        this.criaQrCodeOutputPort = criaQrCodeOutputPort;
        this.buscarPedidoPorIdOutputPort = buscarPedidoPorIdOutputPort;
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
    }

    public CobrancaDTO criar(CriaCobrancaDTO cobrancaIn) {
        var pedidoOut = buscarPedidoPorIdOutputPort.buscarPorId(cobrancaIn.pedidoId());

        validaExisteCobranca(pedidoOut.id());

        var qrCode = criaQrCodeOutputPort.criar(cobrancaIn.pedidoId(), pedidoOut.valorTotal());
        var cobranca = new Cobranca(
                cobrancaIn.pedidoId(), StatusCobrancaEnum.PENDENTE, pedidoOut.valorTotal(), qrCode
        );
        return cobrancaOutputPort.criar(new CobrancaDTO(cobranca));
    }

    private void validaExisteCobranca(Long pedidoId) {
        if (buscaCobrancaOutputPort.pedidoPossuiCobranca(pedidoId)) {
            throw new EntityAlreadyExistException("Já existe uma cobrança para o pedido " + pedidoId);
        }
    }
}
