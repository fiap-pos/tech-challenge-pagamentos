package br.com.fiap.techchallenge.pagamentos.core.dto;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;

public record AtualizaStatusCobrancaDTO (StatusEnum status) {

}