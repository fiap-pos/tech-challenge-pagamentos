package br.com.fiap.techchallenge.pagamentos.core.port.in;

import br.com.fiap.techchallenge.pagamentos.core.dto.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;

public interface AtualizaStatusCobrancaInputPort {
    CobrancaDTO atualizarStatus(Long id, AtualizaStatusCobrancaDTO cobrancaStatusIn);
}
