package br.com.fiap.techchallenge.pagamentos.adapters.web.models.request;

import br.com.fiap.techchallenge.pagamentos.core.domain.entities.enums.StatusCobrancaEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.AtualizaStatusCobrancaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class AtualizaStatusCobrancaRequest {
    private StatusCobrancaEnum status;

    AtualizaStatusCobrancaRequest() {

    }

    public AtualizaStatusCobrancaRequest(StatusCobrancaEnum status) {
        this.status = status;
    }

    @NotNull(message = "O campo 'status' é obrigatório")
    @Schema(type = "String", title = "Status da cobrança", allowableValues = {"PAGO","CANCELADO"})
    public StatusCobrancaEnum getStatus() {
        return status;
    }

    public void setStatus(StatusCobrancaEnum status) {
        this.status = status;
    }

    public AtualizaStatusCobrancaDTO toAtualizaStatusCobrancaDTO() {
        return new AtualizaStatusCobrancaDTO(status);
    }
}
