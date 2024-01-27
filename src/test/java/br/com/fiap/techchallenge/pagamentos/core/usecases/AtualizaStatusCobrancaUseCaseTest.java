package br.com.fiap.techchallenge.pagamentos.core.usecases;

import br.com.fiap.techchallenge.pagamentos.core.domain.exception.BadRequestException;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.AtualizaStatusCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.AtualizaStatusCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.usecase.AtualizaStatusCobrancaUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTOcomStatusPendente;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AtualizaStatusCobrancaUseCaseTest {

    private AtualizaStatusCobrancaInputPort atualizaStatusCobrancaInputPort;

    @Mock
    BuscaCobrancaOutputPort buscaCobrancaOutputPort;


    @Mock
    AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        atualizaStatusCobrancaInputPort = new AtualizaStatusCobrancaUseCase(
                buscaCobrancaOutputPort,
                atualizaStatusCobrancaOutputPort
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class atualizaStatusCobrancaUseCase {

        @Test
        void lancaExececao_atualizarStatusCobranca_DiferenteStatusPendente() throws JsonProcessingException {
            var cobrancaDTO = getCobrancaDTO();
            var id = cobrancaDTO.id();
            var atualizaStatusCobrancaDTO = new AtualizaStatusCobrancaDTO(StatusEnum.PAGO);

            when(buscaCobrancaOutputPort.buscarPorId(cobrancaDTO.id())).thenReturn(cobrancaDTO);

            assertThatThrownBy(() -> atualizaStatusCobrancaInputPort.atualizarStatus(id, atualizaStatusCobrancaDTO))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage("Cobranca "+id+" não pode mais ser atualizada.");

            verify(buscaCobrancaOutputPort, times(1)).buscarPorId(anyLong());
            verify(atualizaStatusCobrancaOutputPort, never()).atualizarStatus(anyLong(), any(StatusEnum.class));
        }

        @Test
        void atualizarStatusCobranca_ComStatusPendente() throws JsonProcessingException {
            var cobrancaDTOcomStatusPendente = getCobrancaDTOcomStatusPendente();
            var id = cobrancaDTOcomStatusPendente.id();
            var atualizaStatusCobrancaDTOparaPago = new AtualizaStatusCobrancaDTO(StatusEnum.PAGO);
            var cobrancaDTOcomStatusPago = getCobrancaDTO();

            when(buscaCobrancaOutputPort.buscarPorId(id)).thenReturn(cobrancaDTOcomStatusPendente);
            when(atualizaStatusCobrancaOutputPort.atualizarStatus(id, atualizaStatusCobrancaDTOparaPago.status())).thenReturn(cobrancaDTOcomStatusPago);

            var cobrancaDTOcomStatusAtualizado = atualizaStatusCobrancaInputPort.atualizarStatus(1L, atualizaStatusCobrancaDTOparaPago);

            assertThat(cobrancaDTOcomStatusAtualizado).isNotNull().isInstanceOf(CobrancaDTO.class);
            assertThat(cobrancaDTOcomStatusAtualizado.id()).isEqualTo(cobrancaDTOcomStatusPago.id());
            assertThat(cobrancaDTOcomStatusAtualizado.pedidoId()).isEqualTo(cobrancaDTOcomStatusPago.pedidoId());
            assertThat(cobrancaDTOcomStatusAtualizado.status()).isEqualTo(cobrancaDTOcomStatusPago.status());
            assertThat(cobrancaDTOcomStatusAtualizado.valor()).isEqualTo(cobrancaDTOcomStatusPago.valor());
            assertThat(cobrancaDTOcomStatusAtualizado.qrCode()).isEqualTo(cobrancaDTOcomStatusPago.qrCode());
        }
    }
}