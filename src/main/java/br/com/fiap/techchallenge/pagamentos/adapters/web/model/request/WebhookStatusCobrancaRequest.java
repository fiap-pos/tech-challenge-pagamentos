package br.com.fiap.techchallenge.pagamentos.adapters.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WebhookStatusCobrancaRequest {
    @NotNull
    @NotBlank
    private String action;

    @NotNull
    private WebhookDataRequest data;

    public WebhookStatusCobrancaRequest(String action, WebhookDataRequest data) {
        this.action = action;
        this.data = data;
    }


    public String getAction() {
        return action;
    }

    public WebhookDataRequest getData() {
        return data;
    }
}
