package br.com.fiap.techchallenge.pagamentos.config;

import br.com.fiap.techchallenge.pagamentos.adapters.gateways.PagamentoMock;
import br.com.fiap.techchallenge.pagamentos.core.port.in.*;
import br.com.fiap.techchallenge.pagamentos.core.port.out.*;
import br.com.fiap.techchallenge.pagamentos.core.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreInjectionConfig {

    @Bean
    CriaQrCodeOutputPort criaQrCodeInputPort(){
        return new PagamentoMock();
    }

    @Bean
    BuscaCobrancaPorIdInputPort buscaCobrancaPorId(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        return new BuscaCobrancaPorIdUseCase(buscaCobrancaOutputPort);
    }

    @Bean
    BuscaCobrancaPorPedidoIdInputPort buscarCobrancaPorPedidoId(BuscaCobrancaOutputPort buscaCobrancaOutputPort) {
        return new BuscaCobrancaPorPedidoIdUseCase(buscaCobrancaOutputPort);
    }
    @Bean
    CriaCobrancaInputPort criarCobranca(
            CriaCobrancaOutputPort criaCobrancaOutputPort,
            CriaQrCodeOutputPort criaQrCodeOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort
    ) {
        return new CriaCobrancaUseCase(
                criaCobrancaOutputPort,
                criaQrCodeOutputPort,
                buscaCobrancaOutputPort
        );
    }

    @Bean
    AtualizaStatusCobrancaInputPort atualiStatusCobranca(
            AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort
    ) {
        return new AtualizaStatusCobrancaUseCase(
                buscaCobrancaOutputPort,
                atualizaStatusCobrancaOutputPort
        );
    }

    @Bean
    BuscaStatusPagamentoInputPort buscaStatusPagamento(BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort) {
        return new BuscaStatusPagamentoUseCase(buscaStatusPagamentoOutputPort);
    }

//    @Bean
//    EnviaMensagemInputPort enviaMensagem(EnviaMensagemOutputPort enviaMensagemOutputPort){
//        return new EnviaMensagemUseCase(enviaMensagemOutputPort);
//    }
}
