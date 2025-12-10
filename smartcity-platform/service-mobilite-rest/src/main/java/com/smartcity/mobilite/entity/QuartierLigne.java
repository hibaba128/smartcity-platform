package com.smartcity.mobilite.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "quartier_ligne")
public class QuartierLigne {

    @Id
    private String quartier;

    @ElementCollection
    @CollectionTable(name = "quartier_ligne_map", joinColumns = @JoinColumn(name = "quartier"))
    @Column(name = "ligne")
    private List<String> lignes;

    public QuartierLigne() {}

    public QuartierLigne(String quartier, List<String> lignes) {
        this.quartier = quartier;
        this.lignes = lignes;
    }

    public String getQuartier() { return quartier; }
    public void setQuartier(String quartier) { this.quartier = quartier; }

    public List<String> getLignes() { return lignes; }
    public void setLignes(List<String> lignes) { this.lignes = lignes; }
}
