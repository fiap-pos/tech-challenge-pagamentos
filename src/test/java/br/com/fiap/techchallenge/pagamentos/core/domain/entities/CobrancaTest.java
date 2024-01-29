package br.com.fiap.techchallenge.pagamentos.core.domain.entities;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CobrancaTest {

    @Test
    void testCobranca() {
        var QrCode = new QrCode("asddadsapsd=");
        Cobranca cobranca = new Cobranca(1L, 1L, StatusEnum.PENDENTE, BigDecimal.TEN, QrCode);
        assertThat(cobranca.getId()).isEqualTo(1L);
        assertThat(cobranca.getPedidoId()).isEqualTo(1L);
        assertThat(cobranca.getStatus()).isEqualTo(StatusEnum.PENDENTE);
        assertThat(cobranca.getValor()).isEqualTo(BigDecimal.TEN);
        assertThat(cobranca.getQrCode()).isEqualTo(QrCode);

    }

}