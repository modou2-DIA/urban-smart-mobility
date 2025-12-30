package com.city.smart.orchestration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.city.smart.soap.airquality.client.GetCurrentStatus;
import com.city.smart.soap.airquality.client.GetCurrentStatusResponse;

import org.springframework.beans.factory.annotation.Value;

@Configuration
public class SoapClientConfig {

    @Value("${orchestration.soap.host}")
    private String soapHost;

    @Value("${orchestration.soap.port}")
    private int soapPort;

    private static final String SOAP_ENDPOINT_PATH = "/soap-api/airquality";

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        
        // ✅ CORRECTION : Le contextPath doit correspondre au packageName généré
        //marshaller.setContextPath("com.city.smart.soap.airquality.client");
        marshaller.setClassesToBeBound(
            GetCurrentStatus.class,
            GetCurrentStatusResponse.class
            // Ajoutez ici d'autres classes si vous implémentez getHistoricalData
        );
        
        return marshaller;
    }

    @Bean
    public WebServiceTemplate airQualityServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);
        
        String defaultUri = String.format("http://%s:%d%s", soapHost, soapPort, SOAP_ENDPOINT_PATH);
        
        System.out.println("✅ SOAP Client: Configuration de l'URI sur " + defaultUri);
        
        template.setDefaultUri(defaultUri);
        
        return template;
    }
}