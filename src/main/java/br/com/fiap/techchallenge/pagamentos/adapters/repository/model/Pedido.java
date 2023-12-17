package br.com.fiap.techchallenge.pagamentos.adapters.repository.model;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusPedidoEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum status;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private LocalDateTime data;

    @PrePersist
    protected void onCreate() {
        data = LocalDateTime.now();
        status = StatusPedidoEnum.PENDENTE_DE_PAGAMENTO;
        setValorTotal();
    }

    public Pedido() {
    }
    public Pedido(StatusPedidoEnum status, LocalDateTime data) {
        this.status = status;
        this.data = data;
    }
    public Pedido(StatusPedidoEnum status, LocalDateTime data, BigDecimal valorTotal) {
        this.status = status;
        this.data = data;
        this.valorTotal = valorTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedidoEnum getStatus() {
        return status;
    }

    public void setStatus(StatusPedidoEnum status) {
        this.status = status;
    }
    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal setValorTotal() {
        return valorTotal;
    }
}
