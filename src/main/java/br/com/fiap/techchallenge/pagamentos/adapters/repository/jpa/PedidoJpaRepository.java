package br.com.fiap.techchallenge.pagamentos.adapters.repository.jpa;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoJpaRepository extends JpaRepository<Pedido, Long> {
}
