package br.com.fiap.techchallenge.pagamentos.core.ports.out;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;

public interface CriaCobrancaOutputPort {
    CobrancaDTO criar(CobrancaDTO cobranca);
}
