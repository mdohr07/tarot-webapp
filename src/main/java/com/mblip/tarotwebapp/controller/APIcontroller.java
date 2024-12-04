package com.mblip.tarotwebapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class APIcontroller {

    @GetMapping("/api")
    public String api(Model model) {
        String baseURL = "https://tarotapi.dev/api/v1/";
        String endpoint = baseURL + "cards"; // return all cards

        // Erstellt RestTemplate-Objekt, um HTTP-Anfragen an eine API zu senden
        RestTemplate restTemplate = new RestTemplate();
        // sendet eine GET-Anfrage an den endpoint und erwartet einen String als Antwort
        // Die Antwort wird in ResponseEntity<String> gespeichert, das den Statuscode und den Antwortinhalt enthält
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        String jsonString = response.getBody(); // extrahiert den Inhalt der API-Antwort
        model.addAttribute("jsonString", jsonString); // fügt den JSON-String dem model hinzu

        System.out.println(jsonString);

        return "api";
    }


}
