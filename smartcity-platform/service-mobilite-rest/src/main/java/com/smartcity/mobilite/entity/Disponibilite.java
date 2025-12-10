package com.smartcity.mobilite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "disponibilite")
public class Disponibilite {
    
    @Id
    private String transport;
    
    @Column(nullable = false)
    private String info;
    
    public Disponibilite() {}
    
    public Disponibilite(String transport, String info) {
        this.transport = transport;
        this.info = info;
    }
    
    public String getTransport() { return transport; }
    public void setTransport(String transport) { this.transport = transport; }
    
    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }
}