package br.com.fiap.techchallenge.pagamentos.adapters.repository.model;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(indexes = @Index(columnList = "status"))
public class Cobranca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private BigDecimal valor;

    @Column(name = "qr_code")
    @Lob
    private String qrCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Cobranca() {
    }

    public Cobranca(Long pedidoId, StatusEnum status, BigDecimal valor, String qrCode) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.valor = valor;
        this.qrCode = qrCode;
    }

    public Cobranca(Long id, Long pedidoId, StatusEnum status, BigDecimal valor, String qrCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.status = status;
        this.valor = valor;
        this.qrCode = qrCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

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

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getQrCode() {
        return qrCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
