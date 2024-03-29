package br.com.fiap.techchallenge.pagamentos.utils;

import br.com.fiap.techchallenge.pagamentos.adapters.repository.model.Cobranca;
import br.com.fiap.techchallenge.pagamentos.core.domain.entities.QrCode;
import br.com.fiap.techchallenge.pagamentos.core.domain.models.enums.StatusEnum;
import br.com.fiap.techchallenge.pagamentos.core.dto.AtualizaStatusCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.CriaCobrancaDTO;
import br.com.fiap.techchallenge.pagamentos.core.dto.StatusPagamentoDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class CobrancaHelper {

    private static final Long ID = 1L;
    private static final Long PEDIDO_ID = 1L;
    private static final BigDecimal VALOR = BigDecimal.valueOf(45.9);
    private static final StatusEnum PAGO = StatusEnum.PAGO;
    private static final StatusEnum PENDENTE = StatusEnum.PENDENTE;
    private static final QrCode QR_CODE = new QrCode("e3BlZGlkb0lkOjEsdmFsb3I6NDUuOX0=");

    private static final LocalDateTime CREATED_AT = LocalDateTime.MAX;
    private static final LocalDateTime UPDATED_AT = LocalDateTime.MAX;

    public static CobrancaDTO getCobrancaDTO() {
        return new CobrancaDTO(ID, PEDIDO_ID, VALOR, PAGO, QR_CODE.getDecodedBase64Value());
    }

    public static Cobranca getCobranca() {
        return new Cobranca(ID, PEDIDO_ID, PAGO, VALOR, QR_CODE.getDecodedBase64Value(), CREATED_AT, UPDATED_AT);
    }

    public static CriaCobrancaDTO getCriaCobrancaDTO() {
        return new CriaCobrancaDTO(1L, VALOR);
    }

    public static StatusPagamentoDTO getStatusPagamentoPagoDTO() {
        return new StatusPagamentoDTO(1L, StatusEnum.RECEBIDO.getDescricao(), StatusEnum.PAGO);
    }

    public static QrCode getQrCode() {
        return new QrCode("{pedidoId:"+PEDIDO_ID+",valor:"+VALOR+"}");
    }

    public static  CobrancaDTO getCobrancaDTOcomStatusPendente() {
        var cobrancaDTO = getCobrancaDTO();
        return new CobrancaDTO(
                cobrancaDTO.id(),
                cobrancaDTO.pedidoId(),
                cobrancaDTO.valor(),
                StatusEnum.PENDENTE,
                cobrancaDTO.qrCode()
        );
    }

    public static AtualizaStatusCobrancaDTO getAtualizaStatusCobrancaDTO(){
        return new AtualizaStatusCobrancaDTO(PAGO);
    }

    public static Cobranca getCobrancacomStatusPendente() {
        return new Cobranca(ID, PEDIDO_ID, PENDENTE, VALOR, QR_CODE.getDecodedBase64Value(), CREATED_AT, UPDATED_AT);
    }
}
