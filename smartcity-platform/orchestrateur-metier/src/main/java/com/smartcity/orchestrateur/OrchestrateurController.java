package com.smartcity.orchestrateur;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrchestrateurController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/trajet-intelligent")
    public Map<String, Object> trajetIntelligent(@RequestParam String quartier) {
        Map<String, Object> result = new HashMap<>();

        // ==================== 1. APPEL SOAP AIR QUALITY ====================
        String soapEnvelope = """
            <?xml version="1.0" encoding="UTF-8"?>
            <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/"
                           xmlns:tns="http://smartcity.com/schema/airquality">
              <soap:Body>
                <tns:GetAQIRequest>
                  <tns:quartier>%s</tns:quartier>
                </tns:GetAQIRequest>
              </soap:Body>
            </soap:Envelope>
            """.formatted(quartier);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("text", "xml", StandardCharsets.UTF_8));
        headers.add("SOAPAction", "GetAQI");

        HttpEntity<String> soapEntity = new HttpEntity<>(soapEnvelope, headers);

        try {
            ResponseEntity<String> soapResponse = restTemplate.exchange(
                "http://localhost:8082/ws/airquality",
                HttpMethod.POST,
                soapEntity,
                String.class
            );

           String body = soapResponse.getBody();
String aqiStr = "0";

if (body != null) {
    // Cherche <aqi>xxx</aqi> même avec namespaces devant
    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<[^>]*aqi[^>]*>([^<]+)</[^>]*aqi[^>]*>");
    java.util.regex.Matcher matcher = pattern.matcher(body);
    if (matcher.find()) {
        aqiStr = matcher.group(1).trim();
    }
}

int aqi = 0;
try {
    aqi = Integer.parseInt(aqiStr);
} catch (NumberFormatException e) {
    aqi = 0;
}
System.out.println("Réponse SOAP brute : " + body); // ← tu verras exactement ce que renvoie ton service
            result.put("aqi", aqiStr); // on garde la vraie valeur brute
            result.put("recommandation", aqi > 100 ? "Pollution élevée - Préférez les transports en commun" 
                                                   : "Bonne qualité de l'air - Vélo / trottinette OK");

        } catch (Exception e) {
            // En prod tu mettras un logger, là on garde visible pour le dev
            result.put("aqi", "Service qualité air indisponible");
            result.put("recommandation", "Indisponible");
        }

        // ==================== 2. APPEL REST MOBILITÉ ====================
        try {
            String horaires = restTemplate.getForObject(
                "http://localhost:8081/mobilite/horaires", String.class);
            result.put("horaires", horaires);
        } catch (Exception e) {
            result.put("horaires", "Service mobilité indisponible");
        }

        return result;
    }
}