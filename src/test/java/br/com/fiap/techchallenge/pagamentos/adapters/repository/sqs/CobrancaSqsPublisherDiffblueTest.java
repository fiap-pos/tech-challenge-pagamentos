package br.com.fiap.techchallenge.pagamentos.adapters.repository.sqs;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CobrancaSqsPublisher.class})
@ExtendWith(SpringExtension.class)
class CobrancaSqsPublisherDiffblueTest {
    @Autowired
    private CobrancaSqsPublisher cobrancaSqsPublisher;

    @MockBean
    private SqsTemplate sqsTemplate;

    /**
     * Method under test:
     * {@link CobrancaSqsPublisher#enviarParaFilaPagamentos(CobrancaDTO)}
     */
    @Test
    void testEnviarParaFilaPagamentos() {
        // Arrange
        UUID messageId = UUID.randomUUID();
        GenericMessage<String> message = new GenericMessage<>("Payload", new HashMap<>());

        when(sqsTemplate.send(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new SendResult<>(messageId, "https://config.us-east-2.amazonaws.com", message, new HashMap<>()));

        // Act
        cobrancaSqsPublisher
                .enviarParaFilaPagamentos(new CobrancaDTO(1L, 1L, new BigDecimal("2.3"), StatusEnum.PENDENTE, "Qr Code"));

        // Assert
        verify(sqsTemplate).send(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link CobrancaSqsPublisher#enviarParaFilaPagamentos(CobrancaDTO)}
     */
    @Test
    void testEnviarParaFilaPagamentos2() {
        // Arrange
        UUID messageId = UUID.randomUUID();
        GenericMessage<String> message = new GenericMessage<>("Payload", new HashMap<>());

        when(sqsTemplate.send(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new SendResult<>(messageId, "https://config.us-east-2.amazonaws.com", message, new HashMap<>()));

        // Act
        cobrancaSqsPublisher.enviarParaFilaPagamentos(new CobrancaDTO(1L, 1L, null, StatusEnum.PENDENTE, "Qr Code"));

        // Assert
        verify(sqsTemplate).send(Mockito.<String>any(), Mockito.<String>any());
    }

    /**
     * Method under test:
     * {@link CobrancaSqsPublisher#enviarParaFilaPagamentos(CobrancaDTO)}
     */
    @Test
    void testEnviarParaFilaPagamentos3() {
        // Arrange
        UUID messageId = UUID.randomUUID();
        GenericMessage<String> message = new GenericMessage<>("Payload", new HashMap<>());

        when(sqsTemplate.send(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new SendResult<>(messageId, "https://config.us-east-2.amazonaws.com", message, new HashMap<>()));

        // Act
        cobrancaSqsPublisher.enviarParaFilaPagamentos(null);

        // Assert
        verify(sqsTemplate).send(Mockito.<String>any(), Mockito.<String>any());
    }
}
