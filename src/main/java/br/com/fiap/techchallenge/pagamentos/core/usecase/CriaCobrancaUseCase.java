package br.com.fiap.techchallenge.pagamentos.core.usecase;


import br.com.fiap.techchallenge.pagamentos.core.domain.entities.Cobranca;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityAlreadyExistException;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.CriaCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaQrCodeOutputPort;

public class CriaCobrancaUseCase implements CriaCobrancaInputPort {

    private final CriaCobrancaOutputPort cobrancaOutputPort;

    private final CriaQrCodeOutputPort criaQrCodeOutputPort;


    private final BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    public CriaCobrancaUseCase(
            CriaCobrancaOutputPort cobrancaOutputPort,
            CriaQrCodeOutputPort criaQrCodeOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort
    ) {
        this.cobrancaOutputPort = cobrancaOutputPort;
        this.criaQrCodeOutputPort = criaQrCodeOutputPort;
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
    }

    public CobrancaDTO criar(CriaCobrancaDTO cobrancaIn) {

        validaExisteCobranca(cobrancaIn.pedidoId());

        var qrCode = criaQrCodeOutputPort.criar(cobrancaIn.pedidoId(), cobrancaIn.valor());
        var cobranca = new Cobranca(
                cobrancaIn.pedidoId(), StatusEnum.PENDENTE, cobrancaIn.valor(), qrCode
        );
        return cobrancaOutputPort.criar(new CobrancaDTO(cobranca));
    }

    private void validaExisteCobranca(Long pedidoId) {
        if (buscaCobrancaOutputPort.pedidoPossuiCobranca(pedidoId)) {
            throw new EntityAlreadyExistException("Já existe uma cobrança para o pedido " + pedidoId);
        }
    }
}
