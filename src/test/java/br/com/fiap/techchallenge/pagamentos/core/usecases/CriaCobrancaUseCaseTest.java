package br.com.fiap.techchallenge.pagamentos.core.usecases;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.CriaCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaQrCodeOutputPort;
//import br.com.fiap.techchallenge.pagamentos.core.port.out.pedido.BuscarPedidoPorIdOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.usecase.CriaCobrancaUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCriaCobrancaDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getQrCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class CriaCobrancaUseCaseTest {

    private CriaCobrancaInputPort criaCobrancaInputPort;

    @Mock
    CriaCobrancaOutputPort cobrancaOutputPort;

    @Mock
    CriaQrCodeOutputPort criaQrCodeOutputPort;

//    @Mock
//    BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort;

    @Mock
    BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        criaCobrancaInputPort = new CriaCobrancaUseCase(
                cobrancaOutputPort,
                criaQrCodeOutputPort,
                buscaCobrancaOutputPort
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Nested
    class criaCobrancaUseCase {

        @Test
        void criarCobranca() {
            var criaCobrancaDTO = getCriaCobrancaDTO();
            var cobrancaDTO = getCobrancaDTO();
//            var pedidoDTO = getPedidoDTO();
            var qrCode = getQrCode();

            when(cobrancaOutputPort.criar(any(CobrancaDTO.class))).thenReturn(cobrancaDTO);
//            when(buscarPedidoPorIdOutputPort.buscarPorId(anyLong())).thenReturn(pedidoDTO);
            when(criaQrCodeOutputPort.criar(anyLong(), any(BigDecimal.class))).thenReturn(qrCode);

            var cobrancaCriada = criaCobrancaInputPort.criar(criaCobrancaDTO);

            assertThat(cobrancaCriada).isNotNull();
            assertThat(cobrancaCriada.id()).isEqualTo(cobrancaDTO.id());
            assertThat(cobrancaCriada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
            assertThat(cobrancaCriada.status()).isEqualTo(cobrancaDTO.status());
            assertThat(cobrancaCriada.valor()).isEqualTo(cobrancaDTO.valor());
            assertThat(cobrancaCriada.qrCode()).isEqualTo(cobrancaDTO.qrCode());

            verify(cobrancaOutputPort, times(1)).criar(any(CobrancaDTO.class));
            verifyNoMoreInteractions(cobrancaOutputPort);

//            verify(buscarPedidoPorIdOutputPort, times(1)).buscarPorId(anyLong());
//            verifyNoMoreInteractions(buscarPedidoPorIdOutputPort);

            verify(criaQrCodeOutputPort, times(1)).criar(anyLong(), any(BigDecimal.class));
            verifyNoMoreInteractions(criaQrCodeOutputPort);
        }

    }
}