package br.com.fiap.techchallenge.pagamentos.core.dto;

import java.math.BigDecimal;

public record CriaCobrancaDTO(Long pedidoId, BigDecimal valor) {
}

