package com.smartcity.orchestrateur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orchestrateur")
public class OrchestrateurController {

    public OrchestrateurController() {
        // TODO: Constructor
    }

    @GetMapping("/planifier-trajet")
    public void planifierTrajet(String zoneDepart, String zoneArrivee) {
        // TODO: Orchestrate workflow: check air quality, suggest alternatives, show transport
    }

    @GetMapping("/envoyer-alertes")
    public void envoyerAlertes(String typeAlerte) {
        // TODO: Orchestrate emergency alerts workflow
    }
}
