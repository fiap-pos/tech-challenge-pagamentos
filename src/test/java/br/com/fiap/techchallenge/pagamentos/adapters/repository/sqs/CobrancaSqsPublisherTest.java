package br.com.fiap.techchallenge.pagamentos.adapters.repository.sqs;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import io.awspring.cloud.sqs.operations.SendResult;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.support.GenericMessage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CobrancaSqsPublisherTest {
    @InjectMocks
    private CobrancaSqsPublisher cobrancaSqsPublisher;

    @Mock
    private SqsTemplate sqsTemplate;
    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        cobrancaSqsPublisher = new CobrancaSqsPublisher(sqsTemplate);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    void testEnviarParaFilaPagamentos() {
        UUID messageId = UUID.randomUUID();
        GenericMessage<String> message = new GenericMessage<>("Payload", new HashMap<>());
        CobrancaDTO cobrancaDTO = getCobrancaDTO();

        when(sqsTemplate.send(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new SendResult<>(messageId, "https://config.us-east-2.amazonaws.com", message, new HashMap<>()));

        cobrancaSqsPublisher.enviarParaFilaPagamentos(cobrancaDTO);

        verify(sqsTemplate).send(Mockito.<String>any(), Mockito.<String>any());
    }
}
