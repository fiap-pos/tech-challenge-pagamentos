package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BadRequestExceptionTest {
    @Test
    void testNewBadRequestException() {
        BadRequestException actualBadRequestException = new BadRequestException("Ocorreu um erro");

        assertEquals("Ocorreu um erro", actualBadRequestException.getLocalizedMessage());
        assertEquals("Ocorreu um erro", actualBadRequestException.getMessage());
        assertNull(actualBadRequestException.getCause());
        assertEquals(0, actualBadRequestException.getSuppressed().length);
    }
}
