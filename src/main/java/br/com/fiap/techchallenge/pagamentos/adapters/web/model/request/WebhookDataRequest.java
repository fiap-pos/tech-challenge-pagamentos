package br.com.fiap.techchallenge.pagamentos.adapters.web.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WebhookDataRequest {

    @NotNull
    @NotBlank
    private Long id;
    public WebhookDataRequest(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
}
