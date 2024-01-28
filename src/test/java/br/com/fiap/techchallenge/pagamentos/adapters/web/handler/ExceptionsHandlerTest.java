package br.com.fiap.techchallenge.pagamentos.adapters.web.handler;

import br.com.fiap.techchallenge.pagamentos.core.domain.exception.BadRequestException;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityAlreadyExistException;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionsHandlerTest {

    private final String MSG_BAD_REQUEST = "An error occurred";
    private final String MSG_ENTITY_ALREADY_EXISTS = "Entity already exist";
    private final String MSG_ENTITY_NOT_FOUND = "Entity not found";
    private final String MSG_EXCEPTION = "Exception";
    @InjectMocks
    ExceptionsHandler exceptionsHandler;
    AutoCloseable openMocks;
    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void testHandlerMethodArgumentNotValidException() {
        BindException bindingResult = new BindException("Target", "Object Name");
        bindingResult.addError(new ObjectError("Object Name", "Default Message"));
        MethodArgumentNotValidException e = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorDetails> actualHandlerMethodArgumentNotValidExceptionResult = exceptionsHandler
                .handlerMethodArgumentNotValidException(e, new MockHttpServletRequest());

        ErrorDetails body = actualHandlerMethodArgumentNotValidExceptionResult.getBody();
        assertEquals("Default Message", body.message());
        assertEquals(400, body.status());
        assertEquals(400, actualHandlerMethodArgumentNotValidExceptionResult.getStatusCodeValue());
        assertTrue(actualHandlerMethodArgumentNotValidExceptionResult.hasBody());
        assertTrue(actualHandlerMethodArgumentNotValidExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandlerHttpMessageNotReadableException() {
        HttpMessageNotReadableException e = new HttpMessageNotReadableException("https://example.org/example",
                new Throwable());

        ResponseEntity<ErrorDetails> actualHandlerHttpMessageNotReadableExceptionResult = exceptionsHandler
                .handlerHttpMessageNotReadableException(e, new MockHttpServletRequest());

        ErrorDetails body = actualHandlerHttpMessageNotReadableExceptionResult.getBody();
        assertNull(body.message());
        assertEquals(400, body.status());
        assertEquals(400, actualHandlerHttpMessageNotReadableExceptionResult.getStatusCodeValue());
        assertTrue(actualHandlerHttpMessageNotReadableExceptionResult.hasBody());
        assertTrue(actualHandlerHttpMessageNotReadableExceptionResult.getHeaders().isEmpty());
    }
    @Test
    void testHandlerBadRequestException() {
        BadRequestException e = new BadRequestException(MSG_BAD_REQUEST);

        ResponseEntity<ErrorDetails> actualHandlerBadRequestExceptionResult = exceptionsHandler
                .handlerBadRequestException(e, new MockHttpServletRequest());

        ErrorDetails body = actualHandlerBadRequestExceptionResult.getBody();
        assertEquals(MSG_BAD_REQUEST, body.message());
        assertEquals(400, body.status());
        assertEquals(400, actualHandlerBadRequestExceptionResult.getStatusCodeValue());
        assertTrue(actualHandlerBadRequestExceptionResult.hasBody());
        assertTrue(actualHandlerBadRequestExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void handlerEntityAlreadyExistException(){
        EntityAlreadyExistException e = new EntityAlreadyExistException(MSG_ENTITY_ALREADY_EXISTS);

        ResponseEntity<ErrorDetails> actualHandlerEntityAlreadyExistExceptionResult = exceptionsHandler
                .handlerEntityAlreadyExistException(e, new MockHttpServletRequest());

        ErrorDetails body = actualHandlerEntityAlreadyExistExceptionResult.getBody();
        assertEquals(MSG_ENTITY_ALREADY_EXISTS, body.message());
        assertEquals(409, body.status());
        assertEquals(409, actualHandlerEntityAlreadyExistExceptionResult.getStatusCodeValue());
        assertTrue(actualHandlerEntityAlreadyExistExceptionResult.hasBody());
        assertTrue(actualHandlerEntityAlreadyExistExceptionResult.getHeaders().isEmpty());
    }

    @Test
    void testHandlerEntityNotFoundException() {
        EntityNotFoundException e = new EntityNotFoundException(MSG_ENTITY_NOT_FOUND);

        ResponseEntity<ErrorDetails> actualHandlerEntityNotFoundExceptionResult = exceptionsHandler
                .handlerEntityNotFoundException(e, new MockHttpServletRequest());

        ErrorDetails body = actualHandlerEntityNotFoundExceptionResult.getBody();
        assertEquals(MSG_ENTITY_NOT_FOUND, body.message());
        assertEquals(404, body.status());
        assertEquals(404, actualHandlerEntityNotFoundExceptionResult.getStatusCodeValue());
        assertTrue(actualHandlerEntityNotFoundExceptionResult.hasBody());
        assertTrue(actualHandlerEntityNotFoundExceptionResult.getHeaders().isEmpty());
    }
    @Test
    void testHandlerException() {
        Exception e = new Exception(MSG_EXCEPTION);

        ResponseEntity<ErrorDetails> actualHandlerExceptionResult = exceptionsHandler.handlerException(e,
                new MockHttpServletRequest());

        ErrorDetails body = actualHandlerExceptionResult.getBody();
        assertEquals(MSG_EXCEPTION, body.message());
        assertEquals(500, body.status());
        assertEquals(500, actualHandlerExceptionResult.getStatusCodeValue());
        assertTrue(actualHandlerExceptionResult.hasBody());
        assertTrue(actualHandlerExceptionResult.getHeaders().isEmpty());
    }
}
