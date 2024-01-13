package br.com.fiap.techchallenge.pagamentos.core.dto;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
public record StatusPagamentoDTO(
        Long id,
        String statusPedido,
        StatusEnum statusPagamento
) {
}
