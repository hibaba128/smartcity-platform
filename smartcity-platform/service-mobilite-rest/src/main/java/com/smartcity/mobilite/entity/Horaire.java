package com.smartcity.mobilite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "horaires")
public class Horaire {
    
    @Id
    private String ligne;
    
    @Column(nullable = false)
    private String horaire;
    
    // Constructeurs
    public Horaire() {}
    
    public Horaire(String ligne, String horaire) {
        this.ligne = ligne;
        this.horaire = horaire;
    }
    
    // Getters et Setters
    public String getLigne() { return ligne; }
    public void setLigne(String ligne) { this.ligne = ligne; }
    
    public String getHoraire() { return horaire; }
    public void setHoraire(String horaire) { this.horaire = horaire; }
}