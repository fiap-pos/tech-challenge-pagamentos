package br.com.fiap.techchallenge.pagamentos.adapters.gateways;

import br.com.fiap.techchallenge.pagamentos.adapters.web.model.StatusPedidoMercadoPago;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.PaymentErrorException;
import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaStatusPagamentoOutputPort;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class StatusPagamento implements BuscaStatusPagamentoOutputPort {

    @Value("${mercadopago.api.url}")
    private String UrlMercadoPagoApiPagamentos;

    @Value("${mercadopago.api.token}")
    private String token;
    private final OkHttpClient httpClient;

    public StatusPagamento(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public StatusPagamentoDTO buscaStatus(Long id) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Request request = new Request.Builder()
                .url(UrlMercadoPagoApiPagamentos + id)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .build();

        try {
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful())
                throw new PaymentErrorException(response.message());

            StatusPedidoMercadoPago statusPedidoMercadoPago = mapper.readValue(
                    response.body().byteStream(),
                    StatusPedidoMercadoPago.class
            );

            return statusPedidoMercadoPago.toStatusPagamentoDTO();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}