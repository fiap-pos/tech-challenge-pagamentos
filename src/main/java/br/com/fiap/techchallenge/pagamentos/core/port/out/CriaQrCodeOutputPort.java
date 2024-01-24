package br.com.fiap.techchallenge.pagamentos.core.port.out;

import br.com.fiap.techchallenge.pagamentos.core.domain.entities.QrCode;

import java.math.BigDecimal;

public interface CriaQrCodeOutputPort {
    QrCode criar(Long pedidoId, BigDecimal valor);
}