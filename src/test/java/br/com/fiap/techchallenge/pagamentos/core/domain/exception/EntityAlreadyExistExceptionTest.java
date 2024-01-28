package br.com.fiap.techchallenge.pagamentos.core.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EntityAlreadyExistExceptionTest {

    @Test
    void testNewEntityAlreadyExistException() {
        EntityAlreadyExistException actualEntityAlreadyExistException = new EntityAlreadyExistException("Entidade já existe");

        assertEquals("Entidade já existe", actualEntityAlreadyExistException.getLocalizedMessage());
        assertEquals("Entidade já existe", actualEntityAlreadyExistException.getMessage());
        assertNull(actualEntityAlreadyExistException.getCause());
        assertEquals(0, actualEntityAlreadyExistException.getSuppressed().length);
    }
}
