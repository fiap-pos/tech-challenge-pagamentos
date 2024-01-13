CREATE TYPE status_cobranca AS ENUM ('PENDENTE', 'PAGO', 'RECEBIDO', 'CANCELADO', 'RECUSADO');
CREATE TABLE COBRANCA (
      id SERIAL PRIMARY KEY,
      pedido_id bigint  NOT NULL,
      status status_cobranca  NOT NULL,
      valor NUMERIC(10,2) NOT NULL,
      qr_code text NOT NULL,
      created_at timestamp NOT NULL,
      updated_at timestamp NOT NULL
);