package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

public class EntityAlreadyExistException extends RuntimeException {

    public EntityAlreadyExistException(String message) {
        super(message);
    }

}
