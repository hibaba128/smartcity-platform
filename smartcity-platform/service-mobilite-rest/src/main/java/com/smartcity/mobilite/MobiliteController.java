package com.smartcity.mobilite;

import com.smartcity.mobilite.entity.*;
import com.smartcity.mobilite.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mobilite")
public class MobiliteController {
    
    @Autowired
    private HoraireRepository horaireRepository;
    
    @Autowired
    private TraficRepository traficRepository;
    
    @Autowired
    private DisponibiliteRepository disponibiliteRepository;
    
    // Initialisation des données au démarrage
    @jakarta.annotation.PostConstruct
    public void init() {
        if (horaireRepository.count() == 0) {
            horaireRepository.save(new Horaire("ligne1", "08:00 - 18:00"));
            horaireRepository.save(new Horaire("ligne2", "07:30 - 19:00"));
        }
        
        if (traficRepository.count() == 0) {
            traficRepository.save(new Trafic("ligne1", "fluide"));
            traficRepository.save(new Trafic("ligne2", "retard de 10 min"));
        }
        
        if (disponibiliteRepository.count() == 0) {
            disponibiliteRepository.save(new Disponibilite("bus", "5 véhicules disponibles"));
            disponibiliteRepository.save(new Disponibilite("metro", "Tous les trains circulent"));
        }
    }
    
    // ===== GET =====
    @GetMapping("/horaires")
    public Map<String, String> getHoraires() {
        Map<String, String> result = new HashMap<>();
        horaireRepository.findAll().forEach(h -> result.put(h.getLigne(), h.getHoraire()));
        return result;
    }
    

  /*  @GetMapping("/horaires/{quartier}")
public Map<String, String> getHorairesByQuartier(@PathVariable String quartier) {
    Map<String, String> result = new HashMap<>();
    
    // Exemple simple : selon le quartier, tu choisis quelles lignes montrer
    if(quartier.equalsIgnoreCase("Manar")) {
        result.put("ligne1", horaireRepository.findById("ligne1").get().getHoraire());
    } else if(quartier.equalsIgnoreCase("Centre")) {
        result.put("ligne2", horaireRepository.findById("ligne2").get().getHoraire());
    } else {
        // par défaut, toutes les lignes
        horaireRepository.findAll().forEach(h -> result.put(h.getLigne(), h.getHoraire()));
    }
    
    return result;
}*/

    @GetMapping("/etat-trafic")
    public Map<String, String> getEtatTrafic() {
        Map<String, String> result = new HashMap<>();
        traficRepository.findAll().forEach(t -> result.put(t.getLigne(), t.getEtat()));
        return result;
    }
    
    @GetMapping("/disponibilite")
    public Map<String, String> getDisponibiliteTransports() {
        Map<String, String> result = new HashMap<>();
        disponibiliteRepository.findAll().forEach(d -> result.put(d.getTransport(), d.getInfo()));
        return result;
    }
    
    // ===== PUT =====
    @PutMapping("/horaires/{ligne}")
    public Map<String, String> updateHoraire(@PathVariable String ligne, @RequestParam String horaire) {
        Horaire h = horaireRepository.findById(ligne).orElse(new Horaire(ligne, horaire));
        h.setHoraire(horaire);
        horaireRepository.save(h);
        return getHoraires();
    }
    
    @PutMapping("/etat-trafic/{ligne}")
    public Map<String, String> updateEtatTrafic(@PathVariable String ligne, @RequestParam String etat) {
        Trafic t = traficRepository.findById(ligne).orElse(new Trafic(ligne, etat));
        t.setEtat(etat);
        traficRepository.save(t);
        return getEtatTrafic();
    }
    
    @PutMapping("/disponibilite/{transport}")
    public Map<String, String> updateDisponibilite(@PathVariable String transport, @RequestParam String dispo) {
        Disponibilite d = disponibiliteRepository.findById(transport).orElse(new Disponibilite(transport, dispo));
        d.setInfo(dispo);
        disponibiliteRepository.save(d);
        return getDisponibiliteTransports();
    }



    @Autowired
private QuartierLigneRepository quartierLigneRepository;

@GetMapping("/horaires/{quartier}")
public Map<String, String> getHorairesByQuartier(@PathVariable String quartier) {
    Map<String, String> result = new HashMap<>();
    
    // Récupération dynamique des lignes du quartier
    QuartierLigne ql = quartierLigneRepository.findById(quartier).orElse(null);

    if (ql == null || ql.getLignes().isEmpty()) {
        // Si quartier inconnu ou pas de lignes définies, renvoyer toutes les lignes
        horaireRepository.findAll().forEach(h -> result.put(h.getLigne(), h.getHoraire()));
    } else {
        // Sinon, renvoyer uniquement les lignes du quartier
        for (String ligne : ql.getLignes()) {
            horaireRepository.findById(ligne).ifPresent(h -> result.put(ligne, h.getHoraire()));
        }
    }

    return result;
}
@PostMapping("/quartier")
public QuartierLigne addQuartier(@RequestBody QuartierLigne ql) {
    // Sauvegarde ou met à jour le quartier avec ses lignes
    return quartierLigneRepository.save(ql);
}


}