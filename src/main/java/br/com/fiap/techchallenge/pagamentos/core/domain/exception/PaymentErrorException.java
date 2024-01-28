package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

public class PaymentErrorException extends RuntimeException{
    public PaymentErrorException(String message) {
        super(message);
    }

    public PaymentErrorException(Throwable cause) {
        super(cause);
    }
}
