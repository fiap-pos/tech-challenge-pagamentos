package br.com.fiap.techchallenge.pagamentos.adapters.web.mapper;

import br.com.fiap.techchallenge.pagamentos.adapters.web.model.response.CobrancaResponse;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import org.springframework.stereotype.Component;

@Component("CobrancaMapperWeb")
public class CobrancaMapper {
    public CobrancaResponse toCobrancaResponse(CobrancaDTO cobrancaOut) {
        return new CobrancaResponse(
                cobrancaOut.id(),
                cobrancaOut.pedidoId(),
                cobrancaOut.status(),
                cobrancaOut.valor(),
                cobrancaOut.qrCode()
        );
    }
}
