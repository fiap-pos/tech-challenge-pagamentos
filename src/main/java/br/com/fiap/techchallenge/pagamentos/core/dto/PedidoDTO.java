package br.com.fiap.techchallenge.pagamentos.core.dto;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusPedidoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoDTO(
        Long id,
        StatusPedidoEnum status,
        BigDecimal valorTotal,
        LocalDateTime dataCriacao
) {

}
