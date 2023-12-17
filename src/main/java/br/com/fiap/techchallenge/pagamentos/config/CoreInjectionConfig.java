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
            BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort
    ) {
        return new CriaCobrancaUseCase(
                criaCobrancaOutputPort,
                criaQrCodeOutputPort,
                buscarPedidoPorIdOutputPort,
                buscaCobrancaOutputPort
        );
    }

    @Bean
    AtualizaStatusCobrancaInputPort atualiStatusCobranca(
            AtualizaStatusCobrancaOutputPort atualizaStatusCobrancaOutputPort,
            BuscaCobrancaOutputPort buscaCobrancaOutputPort,
            AtualizaStatusPedidoOutputPort atualizaStatusPedidoOutputPort
    ) {
        return new AtualizaStatusCobrancaUseCase(
                buscaCobrancaOutputPort,
                atualizaStatusCobrancaOutputPort,
                atualizaStatusPedidoOutputPort
        );
    }

    @Bean
    BuscaStatusPagamentoInputPort buscaStatusPagamento(BuscaStatusPagamentoOutputPort buscaStatusPagamentoOutputPort) {
        return new BuscaStatusPagamentoUseCase(buscaStatusPagamentoOutputPort);
    }

    @Bean
    BuscarPedidoPorIdInputPort buscarPedidoPorId(BuscarPedidoPorIdOutputPort buscarPedidoPorIdOutputPort){
        return new BuscarPedidoPorIdUseCase(buscarPedidoPorIdOutputPort);
    }
}
