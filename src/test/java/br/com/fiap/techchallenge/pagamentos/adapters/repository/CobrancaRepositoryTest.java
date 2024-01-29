package br.com.fiap.techchallenge.pagamentos.adapters.repository;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.mapper.CobrancaMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.sqs.CobrancaSqsPublisher;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @InjectMocks
    CobrancaRepository cobrancaRepository;
    @Mock
    CobrancaJpaRepository cobrancaJpaRepository;
    @Mock
    CobrancaMapper cobrancaMapper;
    @Mock
    CobrancaSqsPublisher cobrancaSqsPublisher;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        CobrancaRepository cobrancaRepository = new CobrancaRepository(
                cobrancaJpaRepository,
                cobrancaMapper,
                cobrancaSqsPublisher
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void criar() {
        var cobrancaDTO = getCobrancaDTO();
        var cobranca = getCobranca();

        when(cobrancaMapper.toCobranca(any(CobrancaDTO.class))).thenReturn(cobranca);
        when(cobrancaJpaRepository.save(any(Cobranca.class))).thenReturn(cobranca);
        when(cobrancaMapper.toCobrancaOut(any(Cobranca.class))).thenReturn(cobrancaDTO);

        var cobrancaSalva = cobrancaRepository.criar(cobrancaDTO);
        verify(cobrancaMapper, times(1)).toCobranca(any(CobrancaDTO.class));
        verify(cobrancaJpaRepository, times(1)).save(any(Cobranca.class));
        verify(cobrancaMapper, times(1)).toCobrancaOut(any(Cobranca.class));

        assertThat(cobrancaSalva).isNotNull().isEqualTo(cobrancaDTO);
        assertThat(cobrancaSalva.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
        assertThat(cobrancaSalva.id()).isEqualTo(cobrancaDTO.id());
        assertThat(cobrancaSalva.status()).isEqualTo(cobrancaDTO.status());
        assertThat(cobrancaSalva.qrCode()).isEqualTo(cobrancaDTO.qrCode());
    }


    @Test
    void buscarPorId() {
        var cobrancaDTO = getCobrancaDTO();
        var cobranca = getCobranca();

        when(cobrancaJpaRepository.findById(anyLong())).thenReturn(Optional.of(cobranca));
        when(cobrancaMapper.toCobrancaOut(any(Cobranca.class))).thenReturn(cobrancaDTO);

        var cobrancaBuscada = cobrancaRepository.buscarPorId(cobrancaDTO.id());
        verify(cobrancaJpaRepository, times(1)).findById(anyLong());
        verify(cobrancaMapper, times(1)).toCobrancaOut(any(Cobranca.class));

        assertThat(cobrancaBuscada.id()).isEqualTo(cobrancaDTO.id());
        assertThat(cobrancaBuscada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
        assertThat(cobrancaBuscada.status()).isEqualTo(cobrancaDTO.status());
        assertThat(cobrancaBuscada.qrCode()).isEqualTo(cobrancaDTO.qrCode());
    }
    @Test
    void buscarPorIdInexistente() {
        var id = 7L;
        when(cobrancaJpaRepository.findById(anyLong())).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> cobrancaRepository.buscarPorId(id),"Cobrança com o id " + id + " não existe");
    }

    @Test
    void buscarPorPedidoId() {
        var cobrancaDTO = getCobrancaDTO();
        var cobranca = getCobranca();

        when(cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(anyLong())).thenReturn(Optional.of(cobranca));
        when(cobrancaMapper.toCobrancaOut(any(Cobranca.class))).thenReturn(cobrancaDTO);

        var cobrancaBuscada = cobrancaRepository.buscarPorPedidoId(cobrancaDTO.pedidoId());
        verify(cobrancaJpaRepository, times(1)).findFirstByPedidoIdOrderByCreatedAtDesc(anyLong());
        verify(cobrancaMapper, times(1)).toCobrancaOut(any(Cobranca.class));


        assertThat(cobrancaBuscada.id()).isEqualTo(cobrancaDTO.id());
        assertThat(cobrancaBuscada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
        assertThat(cobrancaBuscada.status()).isEqualTo(cobrancaDTO.status());
        assertThat(cobrancaBuscada.qrCode()).isEqualTo(cobrancaDTO.qrCode());
    }

    @Test
    void buscarPorPedidoIdInexistente() {
        var id = 7L;
        when(cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(anyLong())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> cobrancaRepository.buscarPorPedidoId(id),"Cobrança com o pedidoId " + id + " não existe");
    }

    @Test
    void pedidoPossuiCobranca() {
        var cobranca = getCobrancaDTO();
        when(cobrancaJpaRepository.existsCobrancaByPedidoId(anyLong())).thenReturn(true);

        var existe = cobrancaRepository.pedidoPossuiCobranca(cobranca.pedidoId());
        verify(cobrancaJpaRepository, times(1)).existsCobrancaByPedidoId(anyLong());

        assertThat(existe).isTrue();
    }

    @Test
    void atualizaStatus() throws JsonProcessingException {
        var id = 1L;
        var status = StatusEnum.PAGO;
        var cobrancaDTO = getCobrancaDTO();
        var cobranca = getCobranca();

        when(cobrancaJpaRepository.findById(anyLong())).thenReturn(Optional.of(cobranca));
        when(cobrancaJpaRepository.save(any(Cobranca.class))).thenReturn(cobranca);
        when(cobrancaMapper.toCobrancaOut(any(Cobranca.class))).thenReturn(cobrancaDTO);

        var cobrancaAtualizada = cobrancaRepository.atualizarStatus(id, status);

        verify(cobrancaJpaRepository, times(1)).save(any(Cobranca.class));
        verify(cobrancaMapper, times(1)).toCobrancaOut(any(Cobranca.class));

        assertThat(cobrancaAtualizada).isNotNull().isEqualTo(cobrancaDTO);
        assertThat(cobrancaAtualizada.pedidoId()).isEqualTo(cobrancaDTO.pedidoId());
        assertThat(cobrancaAtualizada.id()).isEqualTo(cobrancaDTO.id());
        assertThat(cobrancaAtualizada.status()).isEqualTo(cobrancaDTO.status());
        assertThat(cobrancaAtualizada.qrCode()).isEqualTo(cobrancaDTO.qrCode());
    }
}