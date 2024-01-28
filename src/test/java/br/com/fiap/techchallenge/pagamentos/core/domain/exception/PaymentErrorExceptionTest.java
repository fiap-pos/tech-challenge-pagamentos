package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PaymentErrorExceptionTest {

    @Test
    void testNewPaymentErrorException() {
        PaymentErrorException actualPaymentErrorException = new PaymentErrorException("An error occurred");

        assertEquals("An error occurred", actualPaymentErrorException.getLocalizedMessage());
        assertEquals("An error occurred", actualPaymentErrorException.getMessage());
        assertNull(actualPaymentErrorException.getCause());
        assertEquals(0, actualPaymentErrorException.getSuppressed().length);
    }

    @Test
    void testNewPaymentErrorException2() {
        Throwable cause = new Throwable();

        PaymentErrorException actualPaymentErrorException = new PaymentErrorException(cause);

        assertEquals("java.lang.Throwable", actualPaymentErrorException.getLocalizedMessage());
        assertEquals("java.lang.Throwable", actualPaymentErrorException.getMessage());
        Throwable cause2 = actualPaymentErrorException.getCause();
        assertNull(cause2.getLocalizedMessage());
        assertNull(cause2.getMessage());
        assertNull(cause2.getCause());
        Throwable[] suppressed = actualPaymentErrorException.getSuppressed();
        assertEquals(0, suppressed.length);
        assertSame(cause, cause2);
        assertSame(suppressed, cause2.getSuppressed());
    }
}
