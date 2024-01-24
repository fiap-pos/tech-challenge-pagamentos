package br.com.fiap.techchallenge.pagamentos.adapters.web.model;


import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;

import java.util.List;

public class StatusPedidoMercadoPago {

    private Long id;

    private String status;
    private List<Pagamento> payments;

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public List<Pagamento> getPayments() {
        return payments;
    }

    private StatusEnum toStatusCobrancaEnum() {
        return switch (this.payments.get(0).getStatus()) {
            case "approved" -> StatusEnum.PAGO;
            case "rejected" -> StatusEnum.RECUSADO;
            default -> null;
        };
    }

    public StatusPagamentoDTO toStatusPagamentoDTO() {
        return new StatusPagamentoDTO(this.getId(), this.getStatus(), this.toStatusCobrancaEnum());
    }
}