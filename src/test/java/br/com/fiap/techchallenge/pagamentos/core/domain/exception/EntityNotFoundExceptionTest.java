package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EntityNotFoundExceptionTest {
    @Test
    void testNewEntityNotFoundException() {
        EntityNotFoundException actualEntityNotFoundException = new EntityNotFoundException("Entidade não encontrada");

        assertEquals("Entidade não encontrada", actualEntityNotFoundException.getLocalizedMessage());
        assertEquals("Entidade não encontrada", actualEntityNotFoundException.getMessage());
        assertNull(actualEntityNotFoundException.getCause());
        assertEquals(0, actualEntityNotFoundException.getSuppressed().length);
    }
}
