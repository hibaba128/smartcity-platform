package com.smartcity.qualiteair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qualiteair")
public class QualiteAirEndpoint {

    private Map<String, String> aqi = new ConcurrentHashMap<>();
    private Map<String, String> principauxPolluants = new ConcurrentHashMap<>();

    public QualiteAirEndpoint() {
        // Initialisation avec données fictives
        aqi.put("quartier1", "50"); // bon
        aqi.put("quartier2", "120"); // moyen

        principauxPolluants.put("quartier1", "NO2: 20, CO2: 300, O3: 15");
        principauxPolluants.put("quartier2", "NO2: 50, CO2: 400, O3: 30");
    }

    // ===== GET =====
    @GetMapping("/aqi/{quartier}")
    public String getAQI(@PathVariable String quartier) {
        return aqi.getOrDefault(quartier, "Données non disponibles");
    }

    @GetMapping("/polluants/{quartier}")
    public String getPolluants(@PathVariable String quartier) {
        return principauxPolluants.getOrDefault(quartier, "Données non disponibles");
    }

    // ===== PUT =====
    @PutMapping("/aqi/{quartier}")
    public String updateAQI(@PathVariable String quartier,
                            @RequestParam String valeur) {
        aqi.put(quartier, valeur);
        return "AQI mis à jour pour " + quartier + ": " + valeur;
    }

    @PutMapping("/polluants/{quartier}")
    public String updatePolluants(@PathVariable String quartier,
                                  @RequestParam String valeurs) {
        principauxPolluants.put(quartier, valeurs);
        return "Polluants mis à jour pour " + quartier + ": " + valeurs;
    }
}


