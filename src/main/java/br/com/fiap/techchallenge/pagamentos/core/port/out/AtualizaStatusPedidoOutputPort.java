package br.com.fiap.techchallenge.pagamentos.core.port.out;

import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;

public interface AtualizaStatusPedidoOutputPort {
    PedidoDTO atualizarStatus(Long id, StatusPedidoEnum status);
}

