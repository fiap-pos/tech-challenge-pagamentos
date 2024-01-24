package br.com.fiap.techchallenge.pagamentos.core.port.out;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface AtualizaStatusCobrancaOutputPort {
    CobrancaDTO atualizarStatus(Long id, StatusEnum cobrancaStatusIn) throws JsonProcessingException;
}
