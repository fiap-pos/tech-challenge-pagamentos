package br.com.fiap.techchallenge.pagamentos.core.domain.entities;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;

import java.math.BigDecimal;

public class Cobranca {

    private Long id;
    private Long pedidoId;
    private StatusEnum status;
    private BigDecimal valor;
    private QrCode qrCode;

    public Cobranca(Long pedidoId, StatusEnum status, BigDecimal valor, QrCode qrCode) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.qrCode = qrCode;
        this.valor = valor;
    }

    public Cobranca(Long id, Long pedidoId, StatusEnum status, BigDecimal valor, QrCode qrCode) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.qrCode = qrCode;
        this.valor = valor;
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

    public QrCode getQrCode() {
        return qrCode;
    }
}