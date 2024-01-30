package br.com.fiap.techchallenge.pagamentos.adapters.web.model.request;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CobrancaRequestTest {

    @Test
    void testCobrancaRequest() {
        CobrancaRequest cobrancaRequest = new CobrancaRequest();
        cobrancaRequest.setPedidoId(1L);
        cobrancaRequest.setValor(BigDecimal.TEN);
        assertThat(cobrancaRequest.getPedidoId()).isEqualTo(1L);
        assertThat(cobrancaRequest.getValor()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void testCobrancaRequestWithParameters() {
        CobrancaRequest cobrancaRequest = new CobrancaRequest(1L, BigDecimal.TEN);
        assertThat(cobrancaRequest.getPedidoId()).isEqualTo(1L);
        assertThat(cobrancaRequest.getValor()).isEqualTo(BigDecimal.TEN);
    }
}
