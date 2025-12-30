package com.city.smart.airqualitysoapservice.config;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.city.smart.airqualitysoapservice.endpoint.AirQualityEndpoint;

import jakarta.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    private final Bus cxfBus;

    public WebServiceConfig(Bus cxfBus) {
        this.cxfBus = cxfBus;
    }


    @Bean
    public ServletRegistrationBean cxfServletRegistration() {
        // Enregistre manuellement la servlet CXF et lui donne le contrôle du chemin
        ServletRegistrationBean registration = new ServletRegistrationBean(new CXFServlet(), "/soap-api/*");
        registration.setLoadOnStartup(1);
        return registration;
    }
  

   @Bean
    public Endpoint airQualityEndpoint(AirQualityEndpoint service) {
        EndpointImpl endpoint = new EndpointImpl(cxfBus, service);
        endpoint.publish("/airquality");
        return endpoint;
    }
}
