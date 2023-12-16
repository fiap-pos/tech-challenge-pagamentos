package br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoJpaRepository extends JpaRepository<Pedido, Long> {
}
