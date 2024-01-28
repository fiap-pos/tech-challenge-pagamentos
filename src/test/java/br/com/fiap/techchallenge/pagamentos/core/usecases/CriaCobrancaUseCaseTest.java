package br.com.fiap.techchallenge.pagamentos.core.usecases;

import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityAlreadyExistException;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.port.in.CriaCobrancaInputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.BuscaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaCobrancaOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.port.out.CriaQrCodeOutputPort;
import br.com.fiap.techchallenge.pagamentos.core.usecase.CriaCobrancaUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCriaCobrancaDTO;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getQrCode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class CriaCobrancaUseCaseTest {

    @InjectMocks
    CriaCobrancaUseCase criaCobrancaUseCase;

    @Mock
    CriaCobrancaOutputPort cobrancaOutputPort;

    @Mock
    CriaQrCodeOutputPort criaQrCodeOutputPort;

    @Mock
    BuscaCobrancaOutputPort buscaCobrancaOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
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
            var qrCode = getQrCode();

            when(cobrancaOutputPort.criar(any(CobrancaDTO.class))).thenReturn(cobrancaDTO);
            when(criaQrCodeOutputPort.criar(anyLong(), any(BigDecimal.class))).thenReturn(qrCode);

            var cobrancaCriada = criaCobrancaUseCase.criar(criaCobrancaDTO);

            assertThat(cobrancaCriada).isNotNull();
            assertThat(cobrancaCriada.id()).isEqualTo(cobrancaDTO.id());
            assertThat(cobrancaCriada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
            assertThat(cobrancaCriada.status()).isEqualTo(cobrancaDTO.status());
            assertThat(cobrancaCriada.valor()).isEqualTo(cobrancaDTO.valor());
            assertThat(cobrancaCriada.qrCode()).isEqualTo(cobrancaDTO.qrCode());

            verify(cobrancaOutputPort, times(1)).criar(any(CobrancaDTO.class));
            verifyNoMoreInteractions(cobrancaOutputPort);

            verify(criaQrCodeOutputPort, times(1)).criar(anyLong(), any(BigDecimal.class));
            verifyNoMoreInteractions(criaQrCodeOutputPort);
        }
    }

    @Test
    void validaExisteCobranca() {
        var criaCobrancaDTO = getCriaCobrancaDTO();
        var pedidoId = criaCobrancaDTO.pedidoId();

        when(buscaCobrancaOutputPort.pedidoPossuiCobranca(pedidoId)).thenReturn(true);

        assertThrows(EntityAlreadyExistException.class,() -> criaCobrancaUseCase.criar(criaCobrancaDTO),"Já existe uma cobrança para o pedido " + pedidoId);
        verify(buscaCobrancaOutputPort).pedidoPossuiCobranca(Mockito.<Long>any());
    }
}