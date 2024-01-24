package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public EntityNotFoundException(Throwable throwable) {
        super(throwable);
    }

}
