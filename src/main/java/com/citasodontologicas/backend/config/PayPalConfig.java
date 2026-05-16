package com.citasodontologicas.backend.config;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String secret;

    @Value("${paypal.mode}")
    private String mode;

    @Bean
    public PayPalHttpClient payPalClient() {
        PayPalEnvironment environment =
                mode.equals("live")
                        ? new PayPalEnvironment.Live(clientId, secret)
                        : new PayPalEnvironment.Sandbox(clientId, secret);

        return new PayPalHttpClient(environment);
    }
}