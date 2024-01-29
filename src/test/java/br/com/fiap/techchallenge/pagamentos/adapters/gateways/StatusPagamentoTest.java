package br.com.fiap.techchallenge.pagamentos.adapters.gateways;

import br.com.fiap.techchallenge.pagamentos.adapters.web.model.StatusPedidoMercadoPago;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
class StatusPagamentoTest {
//    @InjectMocks
//    StatusPagamento statusPagamento;
//
//    @Mock
//    OkHttpClient httpClient;
//    AutoCloseable openMocks;
//
//    @BeforeEach
//    void setup(){
//        openMocks = MockitoAnnotations.openMocks(this);
//        statusPagamento = new StatusPagamento(httpClient);
//        ReflectionTestUtils.setField(statusPagamento, "urlMercadoPagoApiPagamentos", "https://api.mercadopago.com/merchant_orders/1");
//    }
//    @AfterEach
//    void tearDown() throws Exception {
//        openMocks.close();
//    }
//
//    @Test
//    void buscaStatus() throws IOException {
//        var remoteCall = mock(Call.class);
//        var request = new Request.Builder()
//                .url("https://api.mercadopago.com/merchant_orders/1")
//                .build();
//        var response = new Response.Builder()
//                .request(request)
//                .protocol(Protocol.HTTP_1_1)
//                .code(200)
//                .body(ResponseBody.create(
//                        MediaType.get("application/json; charset=utf-8"),
//                        "{}"
//                ))
//                .build();
//        when(remoteCall.execute()).thenReturn(response);
//        when(httpClient.newCall(any())).thenReturn(remoteCall);
//
//        var status = statusPagamento.buscaStatus(1L);
//    }
}