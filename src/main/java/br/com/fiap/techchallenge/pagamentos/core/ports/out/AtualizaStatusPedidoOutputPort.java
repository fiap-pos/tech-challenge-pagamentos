package br.com.fiap.techchallenge.pagamentos.core.ports.out;

import br.com.fiap.techchallenge.pagamentos.core.domain.entities.enums.StatusPedidoEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;

public interface AtualizaStatusPedidoOutputPort {
    PedidoDTO atualizarStatus(Long id, StatusPedidoEnum status);
}

