package br.com.fiap.techchallenge.pagamentos.adapters.repository.mappers;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.models.Pedido;
import br.com.fiap.techchallenge.pagamentos.core.dto.PedidoDTO;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
    public PedidoMapper() {
    }

    public PedidoDTO toPedidoDTO(Pedido pedido) {

        return new PedidoDTO(
                pedido.getId(), pedido.getStatus(), pedido.getValorTotal(), pedido.getData());

    }
}
