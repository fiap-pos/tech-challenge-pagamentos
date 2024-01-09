package br.com.fiap.techchallenge.pagamentos.adapters.web;


import br.com.fiap.techchallenge.pagamentos.adapters.web.mapper.CobrancaMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.request.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.request.CobrancaRequest;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.request.WebhookStatusCobrancaRequest;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.response.CobrancaResponse;
import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaStatusPagamentoInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.in.CriaCobrancaInputPort;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
@Slf4j
@Tag(name = "Cobranca", description = "APIs para geração e confirmação de pagamento de cobranças")
@RestController
@RequestMapping("/cobrancas")
public class CobrancaController extends ControllerBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(CobrancaController.class);

    private final Logger logger = LoggerFactory.getLogger(CobrancaController.class);
    private final CriaCobrancaInputPort criaCobrancaInputPort;
    private final BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort;
    private final AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort;
    private final CobrancaMapper cobrancaMapper;
    private final BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;
    private QueueMessagingTemplate queueMessagingTemplate;

    public CobrancaController(
            CriaCobrancaInputPort criaCobrancaInputPort,
            BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort,
            AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort,
            CobrancaMapper cobrancaMapper,
            BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort,
            QueueMessagingTemplate queueMessagingTemplate
    ) {
        this.criaCobrancaInputPort = criaCobrancaInputPort;
        this.buscaCobrancaPorIdInputPort = buscaCobrancaPorIdInputPort;
        this.atualizaStatusCobrancaInputPort = atualizaStatusCobrancaInputPort;
        this.cobrancaMapper = cobrancaMapper;
        this.buscaStatusPagamentoInputPort = buscaStatusPagamentoInputPort;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @PostMapping("/message")
    public void sendMessageToQueue(@RequestBody CobrancaRequest cobrancaRequest){
        queueMessagingTemplate.convertAndSend(endpoint, cobrancaRequest);
    }

    @SqsListener(value = "${cloud.aws.end-point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    @GetMapping("/message")
    public void loadMessageFromSqs(){
        var mensagem = queueMessagingTemplate.receiveAndConvert(endpoint, CobrancaRequest.class);
        System.out.println(mensagem);
    }

    @Operation(summary = "Cria uma nova Cobrança")
    @PostMapping
    ResponseEntity<CobrancaResponse> criar(@Valid @RequestBody CobrancaRequest cobrancaRequest) {
        var cobrancaOut = criaCobrancaInputPort.criar(cobrancaRequest.toCriaCobrancaDTO());
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        var uri = getExpandedCurrentUri("/{id}", cobrancaResponse.getId());

        return ResponseEntity.created(uri).body(cobrancaResponse);
    }

    @Operation(summary = "Busca uma Cobrança por id")
    @GetMapping(value = "/{id}")
    ResponseEntity<CobrancaResponse> get(@PathVariable("id") Long id) {
        var cobrancaOut = buscaCobrancaPorIdInputPort.buscarPorId(id);
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(summary = "Atualiza o status de uma cobrança para Pago ou Cancelado")
    @PostMapping(value = "/{id}/status")
    ResponseEntity<CobrancaResponse> updateStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest
    ) {
        var cobrancaOut = atualizaStatusCobrancaInputPort.atualizarStatus(id, atualizaStatusCobrancaRequest.toAtualizaStatusCobrancaDTO());
        var cobrancaResponse = cobrancaMapper.toCobrancaResponse(cobrancaOut);
        return ResponseEntity.ok().body(cobrancaResponse);
    }

    @Operation(
            summary = "Atualiza o status de uma cobrança para Pago ou Recusado, baseado na requisição pelo serviço de webhook do MercadoPago"
    )
    @PostMapping(value = "/{id}/webhook-status")
    ResponseEntity<?> updateWebhookStatus(
            @PathVariable("id") Long id,
            @Valid @RequestBody WebhookStatusCobrancaRequest request
    ) {

        ResponseEntity<Object> response = ResponseEntity.ok().build();

        if (request.getAction().contains("created")) {
            logger.info(String.format("Pagamento com id: %s criado\n", id));
            return response;
        }

        try {
            StatusPagamentoDTO statusPedidoPagamento = buscaStatusPagamentoInputPort.buscaStatus(request.getData().getId());
            AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest =
                    new AtualizaStatusCobrancaRequest(statusPedidoPagamento.statusPagamento());

            atualizaStatusCobrancaInputPort.atualizarStatus(id, atualizaStatusCobrancaRequest.toAtualizaStatusCobrancaDTO());
            return response;
        } catch (Exception ex) {
            logger.error(Arrays.toString(ex.getStackTrace()));
            return response;
        }
    }
}

