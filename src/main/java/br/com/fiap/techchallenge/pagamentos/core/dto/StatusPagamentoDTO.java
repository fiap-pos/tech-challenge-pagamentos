package br.com.fiap.techchallenge.pagamentos.core.dto;
import br.com.fiap.techchallenge.pagamentos.core.domain.entities.enums.StatusCobrancaEnum;
public record StatusPagamentoDTO(
        Long id,
        String statusPedido,
        StatusCobrancaEnum statusPagamento
) {
}
