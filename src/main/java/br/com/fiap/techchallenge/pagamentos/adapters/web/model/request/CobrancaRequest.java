package br.com.fiap.techchallenge.pagamentos.adapters.web.model.request;


import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CobrancaRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("pedidoId")
    private Long pedidoId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("valor")
    private BigDecimal valor;

    public CobrancaRequest() {}

    public CobrancaRequest(Long pedidoId, BigDecimal valor) {
        this.pedidoId = pedidoId;
        this.valor = valor;
    }

    @NotNull(message = "O campo pedidoId é obrigatório")
    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public CriaCobrancaDTO toCriaCobrancaDTO() {
        return new CriaCobrancaDTO(pedidoId, valor);
    }
}

