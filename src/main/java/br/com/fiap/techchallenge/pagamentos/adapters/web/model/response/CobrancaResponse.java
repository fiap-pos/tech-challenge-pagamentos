package br.com.fiap.techchallenge.pagamentos.adapters.web.model.response;


import br.com.fiap.techchallenge.pagamentos.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class CobrancaResponse {

    private Long id;
    private Long pedidoId;
    private StatusEnum status;
    private BigDecimal valor;
    private QrCode qrCode;

    public CobrancaResponse() {
    }

    public CobrancaResponse(Long id, Long pedidoId, StatusEnum status, BigDecimal valor, QrCode qrCode) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.valor = valor;
        this.qrCode = qrCode;
    }

    public Long getId() {
        return id;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @JsonIgnore
    public QrCode getQrCode() {
        return qrCode;
    }

    @JsonGetter(value = "qrCode")
    public String getQrCodeAsBase64String() {
        return qrCode.getEncodedBase64Value();
    }
}
