package br.com.fiap.techchallenge.pagamentos.adapters.repository.mapper;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobranca;
import static br.com.fiap.techchallenge.pagamentos.utils.CobrancaHelper.getCobrancaDTO;
import static org.assertj.core.api.Assertions.assertThat;

class CobrancaMapperTest {

    private CobrancaMapper cobrancaMapper;

    @BeforeEach
    void setUp() {
        this.cobrancaMapper = new CobrancaMapper();
    }

    @Test
    void dadoProdutoDTO_DeveFazerMapper_RetornarProduto() {
        var cobrancaDTO = getCobrancaDTO();

        var cobranca = cobrancaMapper.toCobranca(cobrancaDTO);

        assertThat(cobranca).isNotNull().isInstanceOf(Cobranca.class);
        assertThat(cobranca.getPedidoId()).isEqualTo(cobrancaDTO.pedidoId());
    }

    @Test
    void dadoCobranca_DeveFazerMapper_RetornarCobrancaDTO() {
        var cobranca = getCobranca();

        var cobrancaDTO = cobrancaMapper.toCobrancaOut(cobranca);

        assertThat(cobrancaDTO).isNotNull().isInstanceOf(CobrancaDTO.class);
        assertThat(cobrancaDTO.id()).isEqualTo(cobranca.getId());
        assertThat(cobrancaDTO.pedidoId()).isEqualTo(cobranca.getPedidoId());
    }
}