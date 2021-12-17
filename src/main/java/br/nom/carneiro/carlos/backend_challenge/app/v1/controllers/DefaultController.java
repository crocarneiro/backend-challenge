package br.nom.carneiro.carlos.backend_challenge.app.v1.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @GetMapping("/")
    public String getDefault() {
        return "Back-end Challenge 2021 🏅 - Space Flight News";
    }
}
