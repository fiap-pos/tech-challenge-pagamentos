package br.com.fiap.techchallenge.pagamentos.core.domain.entities;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class QrCode {

    private final String value;

    private static final String BASE_64_PATTERN = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";

    public QrCode(String value) {
        this.value = value;
    }

    public String getDecodedBase64Value() {
        if (valueIsBase64Encoded().equals(true)) {
            return new String(Base64.getDecoder().decode(value));
        }
        return value;
    }

    public String getEncodedBase64Value() {
        if (valueIsBase64Encoded().equals(true)) {
            return value;
        }
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private Boolean valueIsBase64Encoded() {
        return value.matches(BASE_64_PATTERN);
    }
}
