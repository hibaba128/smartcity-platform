package com.smartcity.mobilite;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mobilite")
public class MobiliteController {

    private Map<String, String> horaires = new ConcurrentHashMap<>();
    private Map<String, String> trafic = new ConcurrentHashMap<>();
    private Map<String, String> disponibilite = new ConcurrentHashMap<>();

    public MobiliteController() {
        // Initialisation avec des données fictives pour GET
        horaires.put("ligne1", "08:00 - 18:00");
        horaires.put("ligne2", "07:30 - 19:00");

        trafic.put("ligne1", "fluide");
        trafic.put("ligne2", "retard de 10 min");

        disponibilite.put("bus", "5 véhicules disponibles");
        disponibilite.put("metro", "Tous les trains circulent");
    }

    // ===== GET =====
    @GetMapping("/horaires")
    public Map<String, String> getHoraires() {
        return horaires;
    }

    @GetMapping("/etat-trafic")
    public Map<String, String> getEtatTrafic() {
        return trafic;
    }

    @GetMapping("/disponibilite")
    public Map<String, String> getDisponibiliteTransports() {
        return disponibilite;
    }

    // ===== PUT =====
    // Exemple : /mobilite/horaires/ligne1?horaire=09:00-17:00
    @PutMapping("/horaires/{ligne}")
    public Map<String, String> updateHoraire(@PathVariable String ligne,
                                             @RequestParam String horaire) {
        horaires.put(ligne, horaire);
        return horaires;
    }

    @PutMapping("/etat-trafic/{ligne}")
    public Map<String, String> updateEtatTrafic(@PathVariable String ligne,
                                                @RequestParam String etat) {
        trafic.put(ligne, etat);
        return trafic;
    }

    @PutMapping("/disponibilite/{transport}")
    public Map<String, String> updateDisponibilite(@PathVariable String transport,
                                                   @RequestParam String dispo) {
        disponibilite.put(transport, dispo);
        return disponibilite;
    }
}
