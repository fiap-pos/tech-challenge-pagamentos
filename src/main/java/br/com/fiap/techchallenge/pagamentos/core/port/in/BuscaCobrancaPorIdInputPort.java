package br.com.fiap.techchallenge.pagamentos.core.port.in;


import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;

public interface BuscaCobrancaPorIdInputPort {
    CobrancaDTO buscarPorId(Long id);
}
