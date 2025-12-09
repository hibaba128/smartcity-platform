package com.smartcity.orchestrateur.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

@Configuration
public class WebServiceConfig {

    @Bean
    public WebServiceTemplate webServiceTemplate() {
        SaajSoapMessageFactory messageFactory = new SaajSoapMessageFactory();
        messageFactory.afterPropertiesSet();

        WebServiceTemplate template = new WebServiceTemplate(messageFactory);
template.setDefaultUri("http://localhost:8082/ws/airquality");
        // ON NE MET PLUS DE MARSHALLER → on envoie du XML brut
        return template;
    }
}