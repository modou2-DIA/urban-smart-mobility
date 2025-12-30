package com.city.smart.airqualitysoapservice.endpoint;

import java.util.List;

import com.city.smart.airqualitysoapservice.model.AirQualityMeasurement;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.ParameterStyle;
import jakarta.xml.bind.annotation.XmlElement;

// L'annotation @WebService transforme cette interface en Service Endpoint Interface (SEI)

@WebService(
    name = "AirQualityEndpoint",  // Nom explicite
    targetNamespace = "http://city.smart/soap/airquality"  // Namespace cohérent
)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, parameterStyle = ParameterStyle.WRAPPED)
// Simple style, souvent suffisant pour un premier jet
public interface AirQualityEndpoint {

    // Opération 1: Récupérer l'historique pour une zone donnée
   @WebMethod
    List<AirQualityMeasurement> getHistoricalData(
            // AJOUTER L'ANNOTATION XML ELEMENT
            @WebParam(name = "zoneName") @XmlElement(namespace = "http://city.smart/soap/airquality") String zoneName,
            @WebParam(name = "daysBack") @XmlElement(namespace = "http://city.smart/soap/airquality") int daysBack
    );

    // Opération 2: Obtenir le statut actuel
    @WebMethod
    String getCurrentStatus(
        // C'EST LA CORRECTION CIBLÉE
        @WebParam(name = "zoneName") @XmlElement(namespace = "http://city.smart/soap/airquality") String zoneName
    );
}