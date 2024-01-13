CREATE TABLE IF NOT EXISTS cobranca (
      id SERIAL PRIMARY KEY,
      pedido_id bigint  NOT NULL,
      status VARCHAR(30)  NOT NULL,
      valor NUMERIC(10,2) NOT NULL,
      qr_code text NOT NULL,
      created_at timestamp NOT NULL,
      updated_at timestamp NOT NULL
);