package br.com.fiap.techchallenge.pagamentos.core.usecases;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.BuscaCobrancaPorPedidoIdInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.usecase.BuscaCobrancaPorPedidoIdUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class BuscaCobrancaPorPedidoIdUseCaseTest {

    private BuscaCobrancaPorPedidoIdInputPort buscaCobrancaPorPedidoIdInputPort;

    @Mock
    BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        buscaCobrancaPorPedidoIdInputPort = new BuscaCobrancaPorPedidoIdUseCase(buscaCobrancaOutputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Nested
    class buscaCobrancaPorPedidoIdUseCase {

        @Test
        void buscaCobrancaPorPedidoId() {
            var cobrancaDTO = getCobrancaDTO();

            when(buscaCobrancaOutputPort.buscarPorPedidoId(anyLong())).thenReturn(cobrancaDTO);

            var cobrancaBuscada = buscaCobrancaPorPedidoIdInputPort.buscarPorPedidoId(cobrancaDTO.pedidoId());

            assertThat(cobrancaBuscada).isNotNull().isInstanceOf(CobrancaDTO.class);
            assertThat(cobrancaBuscada.id()).isEqualTo(cobrancaDTO.id());
            assertThat(cobrancaBuscada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
            assertThat(cobrancaBuscada.status()).isEqualTo(cobrancaDTO.status());
            assertThat(cobrancaBuscada.valor()).isEqualTo(cobrancaDTO.valor());
            assertThat(cobrancaBuscada.qrCode()).isEqualTo(cobrancaDTO.qrCode());

            verify(buscaCobrancaOutputPort, times(1)).buscarPorPedidoId(anyLong());
            verifyNoMoreInteractions(buscaCobrancaOutputPort);
        }

    }

}