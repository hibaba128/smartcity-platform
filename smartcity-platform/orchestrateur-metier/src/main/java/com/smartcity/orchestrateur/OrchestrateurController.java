package com.smartcity.orchestrateur;

import java.util.HashMap;
import java.util.Map;
import org.springframework.ws.client.core.WebServiceTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.smartcity.orchestrateur.schema.GetAQIRequest;
import com.smartcity.orchestrateur.schema.GetAQIResponse;

@RestController
@RequestMapping("/api")
public class OrchestrateurController {

    @Autowired
    private WebServiceTemplate webServiceTemplate;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/trajet-intelligent")
    public Map<String, Object> trajetIntelligent(@RequestParam String quartier) {
        Map<String, Object> result = new HashMap<>();

        // SOAP
        GetAQIRequest request = new GetAQIRequest();
        request.setQuartier(quartier);
        GetAQIResponse response = (GetAQIResponse) webServiceTemplate
            .marshalSendAndReceive(request);
        result.put("aqi", response.getAqi());

        // REST
        String horaires = restTemplate.getForObject(
            "http://service-mobilite:8081/mobilite/horaires", String.class);
        result.put("horaires", horaires);

        result.put("recommandation", response.getAqi() > 100 ? "Pollution élevée" : "Bon trajet");
        return result;
    }
}
