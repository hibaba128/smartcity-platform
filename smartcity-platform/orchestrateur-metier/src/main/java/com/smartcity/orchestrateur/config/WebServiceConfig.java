package com.smartcity.orchestrateur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

@Configuration
public class WebServiceConfig {

@Bean
    public WebServiceTemplate webServiceTemplate() throws Exception {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.afterPropertiesSet(); // ← LIGNE MAGIQUE

        WebServiceTemplate template = new WebServiceTemplate(messageFactory);
        template.setDefaultUri("http://localhost:8082/ws"); // ou "http://service-qualite-air:8082/ws" en Docker
        return template;
    }
}