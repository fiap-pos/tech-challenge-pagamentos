package br.com.fiap.techchallenge.pagamentos.adapters.repository;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.mapper.CobrancaMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.sqs.CobrancaSqsPublisher;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobranca;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CobrancaRepositoryTest {
    @Mock
    CobrancaRepository cobrancaRepository;

    CobrancaJpaRepository cobrancaJpaRepository;

    CobrancaMapper cobrancaMapper;

    CobrancaSqsPublisher cobrancaSqsPublisher;

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
    void criar() {
        var cobranca = getCobrancaDTO();
        when(cobrancaRepository.criar(any(CobrancaDTO.class))).thenReturn(cobranca);

        var cobrancaSalva = cobrancaRepository.criar(cobranca);
        verify(cobrancaRepository, times(1)).criar(cobranca);

        assertThat(cobrancaSalva).isNotNull().isEqualTo(cobranca);
        assertThat(cobrancaSalva.pedidoId()).isEqualTo(cobranca.pedidoId());
        assertThat(cobrancaSalva.id()).isEqualTo(cobranca.id());
        assertThat(cobrancaSalva.status()).isEqualTo(cobranca.status());
        assertThat(cobrancaSalva.qrCode()).isEqualTo(cobranca.qrCode());
    }

    @Test
    void buscarPorId() {
        var cobrancaDTO = getCobrancaDTO();
        when(cobrancaRepository.buscarPorId(anyLong())).thenReturn(cobrancaDTO);

        var cobrancaBuscada = cobrancaRepository.buscarPorId(cobrancaDTO.id());
        verify(cobrancaRepository, times(1)).buscarPorId(cobrancaDTO.id());

        assertThat(cobrancaBuscada.id()).isEqualTo(cobrancaDTO.id());
        assertThat(cobrancaBuscada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
        assertThat(cobrancaBuscada.status()).isEqualTo(cobrancaDTO.status());
        assertThat(cobrancaBuscada.qrCode()).isEqualTo(cobrancaDTO.qrCode());
    }
    @Test
    void buscarPorIdInexistente() {
        var id = 7L;
        when(cobrancaRepository.buscarPorId(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> cobrancaRepository.buscarPorId(id),"Cobrança com o id " + id + " não existe");
    }

    @Test
    void buscarPorPedidoId() {
        var cobrancaDTO = getCobrancaDTO();
        when(cobrancaRepository.buscarPorPedidoId(anyLong())).thenReturn(cobrancaDTO);

        var cobrancaBuscada = cobrancaRepository.buscarPorPedidoId(cobrancaDTO.pedidoId());
        verify(cobrancaRepository, times(1)).buscarPorPedidoId(cobrancaDTO.pedidoId());


        assertThat(cobrancaBuscada.id()).isEqualTo(cobrancaDTO.id());
        assertThat(cobrancaBuscada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
        assertThat(cobrancaBuscada.status()).isEqualTo(cobrancaDTO.status());
        assertThat(cobrancaBuscada.qrCode()).isEqualTo(cobrancaDTO.qrCode());
    }

    @Test
    void buscarPorPedidoIdInexistente() {
        var id = 7L;
        when(cobrancaRepository.buscarPorPedidoId(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> cobrancaRepository.buscarPorPedidoId(id),"Cobrança com o pedidoId " + id + " não existe");
    }

    @Test
    void pedidoPossuiCobranca() {
        var cobranca = getCobrancaDTO();
        when(cobrancaRepository.pedidoPossuiCobranca(anyLong())).thenReturn(true);

        var existe = cobrancaRepository.pedidoPossuiCobranca(cobranca.pedidoId());
        verify(cobrancaRepository, times(1)).pedidoPossuiCobranca(cobranca.pedidoId());

        assertThat(existe).isTrue();
    }
}