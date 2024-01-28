package br.com.fiap.techchallenge.pagamentos.adapters.web;

import br.com.fiap.techchallenge.pagamentos.adapters.web.mapper.CobrancaMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.request.AtualizaStatusCobrancaRequest;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.request.CobrancaRequest;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.request.WebhookDataRequest;
import br.com.fiap.techchallenge.pagamentos.adapters.web.model.request.WebhookStatusCobrancaRequest;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaCobrancaPorIdInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaStatusPagamentoInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.in.CriaCobrancaInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCriaCobrancaDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getStatusPagamentoPagoDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.JsonToStringHelper.asJsonString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CobrancaControllerTest {

    private MockMvc mockMvc;

    @Mock
    CriaCobrancaInputPort criaCobrancaInputPort;

    @Mock
    BuscaCobrancaPorIdInputPort buscaCobrancaPorIdInputPort;

    @Mock
    AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort;

    @Mock
    BuscaStatusPagamentoInputPort buscaStatusPagamentoInputPort;

    CobrancaMapper cobrancaMapper;

    AutoCloseable mock;

    CobrancaRequest cobrancaRequest = new CobrancaRequest();

    AtualizaStatusCobrancaRequest atualizaStatusCobrancaRequest = new AtualizaStatusCobrancaRequest();

    @BeforeEach
    void setup() {
        cobrancaRequest.setPedidoId(1L);
        atualizaStatusCobrancaRequest.setStatus(StatusEnum.PENDENTE);

        this.cobrancaMapper = new CobrancaMapper();
        mock = MockitoAnnotations.openMocks(this);
        CobrancaController cobrancaController = new CobrancaController(
                criaCobrancaInputPort,
                buscaCobrancaPorIdInputPort,
                atualizaStatusCobrancaInputPort,
                cobrancaMapper,
                buscaStatusPagamentoInputPort
        );

        mockMvc = MockMvcBuilders.standaloneSetup(cobrancaController).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class testaCobrancaController {
        @Test
        void criaNovaCobranca() throws Exception {
            var criaCobrancaDTO = getCriaCobrancaDTO();
            var cobrancaDTO = getCobrancaDTO();

            when(criaCobrancaInputPort.criar(any(CriaCobrancaDTO.class))).thenReturn(cobrancaDTO);

            ResultActions result = mockMvc.perform(post("/cobrancas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(cobrancaRequest))
            );

            result.andExpect(status().isCreated());

            verify(criaCobrancaInputPort, times(1)).criar(any(CriaCobrancaDTO.class));
            verifyNoMoreInteractions(criaCobrancaInputPort);
        }

        @Test
        void buscaUmaCobrancaPorId() throws Exception {
            var id = 1L;
            var cobrancaDTO = getCobrancaDTO();

            when(buscaCobrancaPorIdInputPort.buscarPorId(any(Long.class))).thenReturn(cobrancaDTO);

            ResultActions result = mockMvc.perform(get("/cobrancas/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            result.andExpect(status().isOk());

            verify(buscaCobrancaPorIdInputPort, times(1)).buscarPorId(any(Long.class));
            verifyNoMoreInteractions(buscaCobrancaPorIdInputPort);
        }

        @Test
        void atualizaStatusDaCobrançaParaPago() throws Exception {
            var id = 1L;
            var cobrancaDTO = getCobrancaDTO();
            atualizaStatusCobrancaRequest.setStatus(StatusEnum.PAGO);

            when(atualizaStatusCobrancaInputPort.atualizarStatus(any(Long.class), any(AtualizaStatusCobrancaDTO.class))).thenReturn(cobrancaDTO);

            ResultActions result = mockMvc.perform(post("/cobrancas/{id}/status", id, atualizaStatusCobrancaRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(atualizaStatusCobrancaRequest))
            );

            result.andExpect(status().isOk());

            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(any(Long.class), any(AtualizaStatusCobrancaDTO.class));
            verifyNoMoreInteractions(atualizaStatusCobrancaInputPort);
        }

        @Test
        void atualizaStatusDaCobrancaParaCancelado() throws Exception {
            var id = 1L;
            var cobrancaDTO = getCobrancaDTO();
            atualizaStatusCobrancaRequest.setStatus(StatusEnum.CANCELADO);

            when(atualizaStatusCobrancaInputPort.atualizarStatus(any(Long.class), any(AtualizaStatusCobrancaDTO.class))).thenReturn(cobrancaDTO);

            ResultActions result = mockMvc.perform(post("/cobrancas/{id}/status", id, atualizaStatusCobrancaRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(atualizaStatusCobrancaRequest))
            );

            result.andExpect(status().isOk());

            verify(atualizaStatusCobrancaInputPort, times(1)).atualizarStatus(any(Long.class), any(AtualizaStatusCobrancaDTO.class));
            verifyNoMoreInteractions(atualizaStatusCobrancaInputPort);
        }

        @Test
        void atualizaStatusDaCobrancaNoWebHookDoMercadoPagoParaAprovado() throws Exception {
            var id = 1L;
            var cobrancaDTO = getCobrancaDTO();
            var data = new WebhookDataRequest(2L);
            var statusPagamentoDTO = getStatusPagamentoPagoDTO();
            var request = new WebhookStatusCobrancaRequest("approved", data);

            when(buscaStatusPagamentoInputPort.buscaStatus(anyLong()))
                    .thenReturn(statusPagamentoDTO);

            when(atualizaStatusCobrancaInputPort.atualizarStatus(anyLong(), ArgumentMatchers.any()))
                    .thenReturn(cobrancaDTO);

            mockMvc.perform(post("/cobrancas/{id}/webhook-status", id, request)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andReturn().getResponse().getContentAsString();
        }

    }
}