package br.com.fiap.techchallenge.pagamentos.adapters.web.model.request;


import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
public class CobrancaRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("pedidoId")
    private Long pedidoId;

    public CobrancaRequest() {}

    public CobrancaRequest(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    @NotNull(message = "O campo pedidoId é obrigatório")
    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public CriaCobrancaDTO toCriaCobrancaDTO() {
        return new CriaCobrancaDTO(pedidoId);
    }
}

