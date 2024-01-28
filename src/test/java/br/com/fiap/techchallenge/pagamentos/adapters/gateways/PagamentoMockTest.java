package br.com.fiap.techchallenge.pagamentos.adapters.gateways;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.CobrancaRepository;
import br.com.fiap.techchallenge.pagamentos.core.domain.entities.QrCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getQrCode;
import static org.assertj.core.api.Assertions.assertThat;


class PagamentoMockTest {
    @InjectMocks
    PagamentoMock pagamentoMock;


    @BeforeEach
    void setup() {
        pagamentoMock = new PagamentoMock();
    }

    @Test
    void criar(){
        var qrCode = getQrCode();
        var qrCodeCriado = pagamentoMock.criar(1L, BigDecimal.valueOf(45.9));
        assertThat(qrCodeCriado).isNotNull().isInstanceOf(QrCode.class);
        assertThat(qrCodeCriado.getEncodedBase64Value()).isEqualTo(qrCode.getEncodedBase64Value());
    }

}