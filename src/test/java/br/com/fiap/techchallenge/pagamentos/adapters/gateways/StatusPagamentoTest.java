package br.com.fiap.techchallenge.pagamentos.adapters.gateways;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.CobrancaRepository;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class StatusPagamentoTest {

    @InjectMocks
    StatusPagamento statusPagamento;
    @Mock
    OkHttpClient httpClient;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void buscaStatus() throws IOException {
//        Request request = new Request.Builder()
//                .url("https://api.mercadopago.com/merchant_orders/" + 1L)
//                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + "TEST-1391732842356404-082915-54d1af5cde66c321c38bddda2f7da865-92326974")
//                .build();
//        Response response = httpClient.newCall(request).execute();
    }
}