package br.com.fiap.techchallenge.pagamentos.core.port.in;

import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;

public interface CriaCobrancaInputPort {
    CobrancaDTO criar(CriaCobrancaDTO cobrancaIn);
}
