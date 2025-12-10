package com.smartcity.qualiteair.endpoint;

import com.smartcity.qualiteair.entity.AirQuality;
import com.smartcity.qualiteair.repository.AirQualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import schema.GetAQIRequest;
import schema.GetAQIResponse;

@Endpoint
public class AirQualityEndpoint {
    
    private static final String NAMESPACE = "http://smartcity.com/schema/airquality";
    
    @Autowired
    private AirQualityRepository airQualityRepository;
    
    // Initialisation des données
    @jakarta.annotation.PostConstruct
    public void init() {
        if (airQualityRepository.count() == 0) {
            airQualityRepository.save(new AirQuality("centre", 65, "NO2: 20, CO2: 300, O3: 15"));
            airQualityRepository.save(new AirQuality("nord", 120, "NO2: 50, CO2: 450, O3: 35"));
            airQualityRepository.save(new AirQuality("sud", 45, "NO2: 15, CO2: 280, O3: 10"));
        }
    }
    
@PayloadRoot(namespace = "http://smartcity.com/schema/airquality", localPart = "GetAQIRequest")
    @ResponsePayload
    public GetAQIResponse getAQI(@RequestPayload GetAQIRequest request) {
        GetAQIResponse response = new GetAQIResponse();
        
        AirQuality airQuality = airQualityRepository.findByQuartier(request.getQuartier())
            .orElse(new AirQuality(request.getQuartier(), 999, "Données non disponibles"));
        
        response.setAqi(airQuality.getAqi());
        return response;
    }
}