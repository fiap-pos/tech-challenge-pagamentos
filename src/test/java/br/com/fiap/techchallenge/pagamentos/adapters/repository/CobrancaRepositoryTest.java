package br.com.fiap.techchallenge.pagamentos.adapters.repository;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.mapper.CobrancaMapper;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.sqs.CobrancaSqsPublisher;
import br.com.fiap.techchallenge.pagamentos.core.domain.exception.EntityNotFoundException;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobranca;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CobrancaRepositoryTest {

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
        var cobrancaRepository = new CobrancaRepository(cobrancaJpaRepository,cobrancaMapper, cobrancaSqsPublisher);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    @Test
    void criar() {
        var cobranca = getCobranca();
        when(cobrancaJpaRepository.save(cobranca)).thenReturn(cobranca);

        var cobrancaSalvo = cobrancaJpaRepository.save(cobranca);
        verify(cobrancaJpaRepository, times(1)).save(cobranca);

        assertThat(cobrancaSalvo).isNotNull().isEqualTo(cobranca);
        assertThat(cobrancaSalvo.getPedidoId()).isEqualTo(cobranca.getPedidoId());
        assertThat(cobrancaSalvo.getId()).isEqualTo(cobranca.getId());
        assertThat(cobrancaSalvo.getStatus()).isEqualTo(cobranca.getStatus());
        assertThat(cobrancaSalvo.getQrCode()).isEqualTo(cobranca.getQrCode());
    }

    @Test
    void buscarPorId() {
        var cobranca = getCobranca();
        when(cobrancaJpaRepository.findById(anyLong())).thenReturn(Optional.of(cobranca));

        var cobrancaOptional = cobrancaJpaRepository.findById(cobranca.getId());
        verify(cobrancaJpaRepository, times(1)).findById(cobranca.getId());

        assertThat(cobrancaOptional).isPresent().containsSame(cobranca);

        cobrancaOptional.ifPresent(cobrancaSalva -> {
            assertThat(cobrancaSalva.getId()).isEqualTo(cobranca.getId());
            assertThat(cobrancaSalva.getPedidoId()).isEqualTo(cobranca.getPedidoId());
            assertThat(cobrancaSalva.getStatus()).isEqualTo(cobranca.getStatus());
            assertThat(cobrancaSalva.getStatus()).isEqualTo(cobranca.getStatus());
        });
    }
    @Test
    void buscarPorIdInexistente() {
        var id = 7L;
        when(cobrancaJpaRepository.findById(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> cobrancaJpaRepository.findById(id),"Cobrança com o id " + id + " não existe");
    }

    @Test
    void buscarPorPedidoId() {
        var cobranca = getCobranca();
        when(cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(anyLong())).thenReturn(Optional.of(cobranca));

        var cobrancaOptional = cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(cobranca.getPedidoId());
        verify(cobrancaJpaRepository, times(1)).findFirstByPedidoIdOrderByCreatedAtDesc(cobranca.getPedidoId());

        assertThat(cobrancaOptional).isPresent().containsSame(cobranca);

        cobrancaOptional.ifPresent(cobrancaSalva -> {
            assertThat(cobrancaSalva.getId()).isEqualTo(cobranca.getId());
            assertThat(cobrancaSalva.getPedidoId()).isEqualTo(cobranca.getPedidoId());
            assertThat(cobrancaSalva.getStatus()).isEqualTo(cobranca.getStatus());
            assertThat(cobrancaSalva.getStatus()).isEqualTo(cobranca.getStatus());
        });
    }

    @Test
    void buscarPorPedidoIdInexistente() {
        var id = 7L;
        when(cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(id)).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(id),"Cobrança com o pedidoId " + id + " não existe");
    }

    @Test
    void pedidoPossuiCobranca() {
        var cobranca = getCobranca();
        when(cobrancaJpaRepository.existsCobrancaByPedidoId(anyLong())).thenReturn(true);

        var existe = cobrancaJpaRepository.existsCobrancaByPedidoId(cobranca.getPedidoId());
        verify(cobrancaJpaRepository, times(1)).existsCobrancaByPedidoId(cobranca.getPedidoId());

        assertThat(existe).isTrue();
    }
}