package br.com.fiap.techchallenge.pagamentos.adapters.web.model.request;


import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import jakarta.validation.constraints.NotNull;

public class CobrancaRequest {

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

