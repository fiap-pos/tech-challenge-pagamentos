CREATE TABLE IF NOT EXISTS pedido (
  id bigint AUTO_INCREMENT,
  status enum ('PENDENTE_DE_PAGAMENTO','PAGO','RECEBIDO','EM_PREPARACAO','PRONTO','FINALIZADO','CANCELADO'),
  data timestamp NOT NULL,
  valor_total double,
  primary key (id)
);

CREATE TABLE IF NOT EXISTS cobranca (
    id bigint NOT NULL AUTO_INCREMENT,
    pedido_id bigint  NOT NULL,
    status enum ('PENDENTE', 'PAGO', 'CANCELADO')  NOT NULL,
    valor double NOT NULL,
    qr_code text NOT NULL,
    created_at timestamp NOT NULL,
    updated_at timestamp NOT NULL,
    primary key (id),
    FOREIGN KEY (pedido_id) REFERENCES pedido(id),
    index(status)
);

