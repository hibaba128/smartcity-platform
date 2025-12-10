package com.smartcity.mobilite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trafic")
public class Trafic {
    
    @Id
    private String ligne;
    
    @Column(nullable = false)
    private String etat;
    
    public Trafic() {}
    
    public Trafic(String ligne, String etat) {
        this.ligne = ligne;
        this.etat = etat;
    }
    
    public String getLigne() { return ligne; }
    public void setLigne(String ligne) { this.ligne = ligne; }
    
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
}