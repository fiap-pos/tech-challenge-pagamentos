package br.com.fiap.techchallenge.pagamentos.config;

import com.squareup.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OkHttpClientConfig {
    @Bean
    public OkHttpClient httpclient() {
        return new OkHttpClient();
    }
}