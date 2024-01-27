package br.com.fiap.techchallenge.pagamentos.core.domain.models.enums;

public enum StatusEnum {
    PENDENTE("Pendente"),
    PAGO("Pago"),
    RECEBIDO("Recebido"),
    CANCELADO("Cancelado"),
    RECUSADO("Recusado");

    private final String descricao;

    StatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
