package com.smartcity.profil.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfilController {

    @GetMapping("/profils/test")
    public String test() {
        return "Service Profils is working!";
    }
}
