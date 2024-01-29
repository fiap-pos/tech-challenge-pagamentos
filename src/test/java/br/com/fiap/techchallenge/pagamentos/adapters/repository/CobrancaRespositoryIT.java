package br.com.fiap.techchallenge.pagamentos.adapters.repository;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa.CobrancaJpaRepository;
import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobranca;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@Transactional
public class CobrancaRespositoryIT {

    @Autowired
    CobrancaJpaRepository cobrancaJpaRepository;

    @Test
    void devePermitirCriarTabela() {
        long totalTabelasCriada = cobrancaJpaRepository.count();
        assertThat(totalTabelasCriada).isNotNegative();
    }

    @Test
    void criarCobranca() {
        var cobranca = getCobranca();

        var cobrancaCriada = cobrancaJpaRepository.save(cobranca);

        assertThat(cobrancaCriada)
                .isInstanceOf(Cobranca.class)
                .isNotNull();
        assertThat(cobrancaCriada.getId()).isNotNull();
        assertThat(cobrancaCriada.getId()).isEqualTo(cobranca.getId());
        assertThat(cobrancaCriada.getPedidoId()).isEqualTo(cobranca.getPedidoId());
        assertThat(cobrancaCriada.getStatus()).isEqualTo(cobranca.getStatus());
        assertThat(cobrancaCriada.getQrCode()).isEqualTo(cobranca.getQrCode());
    }

    @Test
    void buscarCobrancaPorId() {
        var cobranca = getCobranca();
        var id = cobranca.getId();

        var cobrancaCriada = salvarCobranca();
        var cobrancaOptional = cobrancaJpaRepository.findById(cobrancaCriada.getId());
        // Assert
        assertThat(cobrancaOptional)
                .isPresent()
                .containsSame(cobrancaCriada);
        cobrancaOptional.ifPresent(cobrancaArmazenada -> {
            assertThat(cobrancaArmazenada.getId())
                    .isEqualTo(cobrancaCriada.getId());
            assertThat(cobrancaArmazenada.getPedidoId())
                    .isEqualTo(cobrancaCriada.getPedidoId());
            assertThat(cobrancaArmazenada.getStatus())
                    .isEqualTo(cobrancaCriada.getStatus());
            assertThat(cobrancaArmazenada.getQrCode())
                    .isEqualTo(cobrancaCriada.getQrCode());
        });
    }

    @Test
    void buscarPorPedidoId(){
        var cobranca = getCobranca();
        var id = cobranca.getId();

        var cobrancaCriada = salvarCobranca();
        var cobrancaOptional = cobrancaJpaRepository.findFirstByPedidoIdOrderByCreatedAtDesc(cobrancaCriada.getPedidoId());
        // Assert
        assertThat(cobrancaOptional)
                .isPresent()
                .containsSame(cobrancaCriada);
        cobrancaOptional.ifPresent(cobrancaArmazenada -> {
            assertThat(cobrancaArmazenada.getId())
                    .isEqualTo(cobrancaCriada.getId());
            assertThat(cobrancaArmazenada.getPedidoId())
                    .isEqualTo(cobrancaCriada.getPedidoId());
            assertThat(cobrancaArmazenada.getStatus())
                    .isEqualTo(cobrancaCriada.getStatus());
            assertThat(cobrancaArmazenada.getQrCode())
                    .isEqualTo(cobrancaCriada.getQrCode());
        });
    }

    @Test
    void atualizaStatus(){

    }

    private Cobranca salvarCobranca() {
        var cobranca = getCobranca();
        return cobrancaJpaRepository.save(cobranca);
    }
}
