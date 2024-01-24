package br.com.fiap.techchallenge.pagamentos.core.usecase;

import br.com.fiap.techchallenge.pagamentos.core.domain.exception.BadRequestException;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import com.fasterxml.jackson.core.JsonProcessingException;


public class AtualizaStatusCobrancaUseCase implements AtualizaStatusCobrancaInputPort {

    private final BuscaCobrancaOutputPort buscaCobrancaOutputPort;
    private final AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort;

    public AtualizaStatusCobrancaUseCase(
            BuscaCobrancaOutputPort buscaCobrancaOutputPort,
            AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort
    ) {
        this.buscaCobrancaOutputPort = buscaCobrancaOutputPort;
        this.atualizaStatusCobrancaOutputPort = atualizaStatusCobrancaOutputPort;
    }
    @Override
    public CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn) throws JsonProcessingException {
        var cobrancaOut = buscaCobrancaOutputPort.buscarPorId(id);
        if (cobrancaOut.status() != StatusEnum.PENDENTE) {
            throw new BadRequestException("Cobranca "+id+" não pode mais ser atualizada.");
        }
        var novoStatus = getStatusPedido(cobrancaStatusIn.status());

        return atualizaStatusCobrancaOutputPort.atualizarStatus(id, novoStatus);
    }

    private StatusEnum getStatusPedido(StatusEnum statusCobranca) {
        return switch(statusCobranca) {
            case PAGO -> StatusEnum.RECEBIDO;
            case CANCELADO, RECUSADO -> StatusEnum.CANCELADO;
            default -> null;
        };
    }
}

