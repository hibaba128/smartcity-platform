package com.smartcity.orchestrateur;

import com.smartcity.orchestrateur.entity.TrajetLog;
import com.smartcity.orchestrateur.entity.AlerteUrgence;
import com.smartcity.orchestrateur.repository.TrajetLogRepository;
import com.smartcity.orchestrateur.repository.AlerteUrgenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.*;

@RestController
@RequestMapping("/api")
public class OrchestrateurController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private TrajetLogRepository trajetLogRepository;

    @Autowired
    private AlerteUrgenceRepository alerteUrgenceRepository;

    @GetMapping("/trajet-intelligent")
    public Map<String, Object> trajetIntelligent(@RequestParam String quartier) {
        Map<String, Object> result = new HashMap<>();

        // ===== AIR (SOAP) =====
   Integer aqiValue = null;
String recommandation = "";
try {
    // On appelle le REST interne de QualiteAirEndpoint
    String url = "http://localhost:8082/qualiteair/aqi/" + quartier;
    String aqiStr = restTemplate.getForObject(url, String.class);

    if (aqiStr != null && !aqiStr.equals("Données non disponibles")) {
        aqiValue = Integer.parseInt(aqiStr.trim());
    }

    recommandation = (aqiValue != null && aqiValue > 100)
            ? "Pollution élevée - Préférez les transports en commun"
            : "Bonne qualité de l'air - Vélo / trottinette OK";

    Map<String, Object> airMap = new HashMap<>();
    airMap.put("aqi", aqiValue != null ? aqiValue : "Indisponible");
    airMap.put("recommandation", recommandation);
    result.put("air", airMap);

} catch (Exception e) {
    result.put("air", "Service qualité air indisponible");
}

        // ===== MOBILITE (REST) =====
 try {
        String horaires = restTemplate.getForObject(
                "http://localhost:8081/mobilite/horaires/" + quartier,
                String.class
        );
        result.put("mobilite", Map.of(
                "horaires", horaires,
                "trafic", restTemplate.getForObject("http://localhost:8081/mobilite/etat-trafic", String.class),
                "disponibilite", restTemplate.getForObject("http://localhost:8081/mobilite/disponibilite", String.class)
        ));
    } catch (Exception e) {
        result.put("mobilite", "Service mobilité indisponible");
    }

        // ===== PERSISTANCE =====
        try {
            TrajetLog log = new TrajetLog(quartier, aqiValue, recommandation,
                    result.get("mobilite") != null ? result.get("mobilite").toString() : "");
            trajetLogRepository.save(log);
            result.put("saved", true);
            result.put("logId", log.getId());
        } catch (Exception e) {
            result.put("saved", false);
            result.put("error", e.getMessage());
        }

        return result;
    }

    // ==================== HISTORIQUE DES TRAJETS ====================
    @GetMapping("/trajets/historique")
    public List<TrajetLog> getHistorique() {
        return trajetLogRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @GetMapping("/trajets/quartier/{quartier}")
    public List<TrajetLog> getTrajetsByQuartier(@PathVariable String quartier) {
        return trajetLogRepository.findByQuartier(quartier);
    }

    // ==================== GESTION DES ALERTES ====================
    @PostMapping("/alerte")
    public Map<String, Object> createAlerte(@RequestBody Map<String, String> request) {
        Map<String, Object> result = new HashMap<>();
        String type = request.get("type");

        String alertId = java.util.UUID.randomUUID().toString();
        AlerteUrgence alerte = new AlerteUrgence(alertId, type, "EN_ATTENTE", 7);
        alerteUrgenceRepository.save(alerte);

        result.put("alertId", alertId);
        result.put("message", "Alerte créée et sauvegardée");
        result.put("estimatedTime", 7);

        return result;
    }

    @GetMapping("/alerte/{alertId}")
    public Map<String, Object> getAlerteStatus(@PathVariable String alertId) {
        return alerteUrgenceRepository.findByAlertId(alertId)
                .map(alerte -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("alertId", alerte.getAlertId());
                    result.put("type", alerte.getType());
                    result.put("status", alerte.getStatus());
                    result.put("createdAt", alerte.getCreatedAt());
                    return result;
                })
                .orElse(Map.of("error", "Alerte non trouvée"));
    }
}
