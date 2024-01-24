package br.com.fiap.techchallenge.pagamentos.adapters.repository.sqs;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CobrancaSqsPublisher  {

    @Value("${pagamentos.queue}")
    private String filaPagamentos;

    private final SqsTemplate sqsTemplate;

    public CobrancaSqsPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void enviarParaFilaPagamentos(CobrancaDTO cobranca) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String mensagem = null;
        try {
            mensagem = mapper.writeValueAsString(cobranca);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        sqsTemplate.send(filaPagamentos, mensagem);
    }
}
