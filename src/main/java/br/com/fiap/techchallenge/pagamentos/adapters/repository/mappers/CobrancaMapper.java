package br.com.fiap.techchallenge.pagamentos.adapters.repository.mappers;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.models.Cobranca;
import br.com.fiap.techchallenge.pagamentos.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import org.springframework.stereotype.Component;

@Component
public class CobrancaMapper {
    public Cobranca toCobranca(CobrancaDTO cobranca) {
        return new Cobranca(
                cobranca.pedidoId(),
                cobranca.status(),
                cobranca.valor(),
                cobranca.qrCode().getEncodedBase64Value()
        );
    }

    public CobrancaDTO toCobrancaOut(Cobranca cobranca) {
        return new CobrancaDTO(
                cobranca.getId(),
                cobranca.getPedidoId(),
                cobranca.getValor(),
                cobranca.getStatus(),
                new QrCode(cobranca.getQrCode())
        );
    }
}
