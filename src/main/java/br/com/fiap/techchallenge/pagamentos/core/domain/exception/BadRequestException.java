package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
