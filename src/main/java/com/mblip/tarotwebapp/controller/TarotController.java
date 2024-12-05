package com.mblip.tarotwebapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mblip.tarotwebapp.model.TarotApiResponse;
import com.mblip.tarotwebapp.model.TarotCard;
import com.mblip.tarotwebapp.model.TarotCardWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class TarotController {
    // TODO: TarotCard getDailyCard()
    // TODO: List<TarotCard> getThreeCards()

    // Index Page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // All Cards Page
    @GetMapping("/all-cards")
    public String getAllCards(Model model) {
        String baseURL = "https://tarotapi.dev/api/v1/";
        String endpoint = baseURL + "cards"; // Alle Karten

        // RestTemplate-Objekt erstellen, um HTTP-Anfrage an die API zu senden
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(endpoint, String.class);

        String jsonString = response.getBody(); // Die JSON-String aus der Antwort
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // JSON-Daten in eine List von TarotCard-Objekten deserialisieren(?)
            JsonNode rootNode = objectMapper.readTree(jsonString);
            JsonNode cardsNode = rootNode.get("cards");

            List<TarotCard> tarotDeck = new ArrayList<>();
            for (JsonNode cardNode : cardsNode) {
                TarotCard card = objectMapper.treeToValue(cardNode, TarotCard.class);
                tarotDeck.add(card);
            }

            // Liste der Karten an die View übergeben
            model.addAttribute("cards", tarotDeck);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "all-cards";
    }

    @GetMapping("/major_arcana")
    public String getMajorArcana(Model model) {
        return "major-arcana";
    }

    // Major Arcana page
    @GetMapping("/all-cards/major-arcana")
    public String showMajorArcana(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "https://tarotapi.dev/api/v1/cards";

        // API-Antwort in die Wrapper-Klasse konvertieren (von meiner Klasse unter "model")
        ResponseEntity<TarotApiResponse> response = restTemplate.getForEntity(endpoint, TarotApiResponse.class);

        // Karten filtern
        List<TarotCard> majorArcana = Objects.requireNonNull(response.getBody())
                .getCards()
                .stream()
                .filter(card -> "major".equals(card.getType()))
                .toList();

        model.addAttribute("majorArcana", majorArcana);

        return "major-arcana";
    }

    @ModelAttribute("majorArcana") // damit die zurückgegebene Liste automatisch ein Attribut "majorArcana" bekommt
    public List<TarotCard> feedMajorArcana() {
    /*  Immer wenn eine Seite geladen wird, die einen Controller benutzt,
        wird die Methode feedMajorArcana() aufgerufen */
        RestTemplate restTemplate = new RestTemplate(); // API-Anfrage
        String endpoint = "https://tarotapi.dev/api/v1/cards";
        TarotCardWrapper response = restTemplate.getForObject(endpoint, TarotCardWrapper.class);

        if (response != null && response.getCards() != null) {
            /* listet alle Karten aus der API-Antwort, .stream() wandelt die Liste in einen Stream um */
            return response.getCards().stream()
                    .filter(card -> "major".equals(card.getType())) // Karten-Typ "major"
                    .toList(); // Wandelt den gefilterten Stream zurück in eine Liste
        }

        return List.of(); // Leere Liste, falls keine Karten verfügbar sind
    }

    // Minor Arcana page
    @GetMapping("/all-cards/minor-arcana")
    public String showMinorArcana(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "https://tarotapi.dev/api/v1/cards";

        // API-Antwort in die Wrapper-Klasse konvertieren (von meiner Klasse unter "model")
        ResponseEntity<TarotApiResponse> response = restTemplate.getForEntity(endpoint, TarotApiResponse.class);

        // Karten filtern
        List<TarotCard> minorArcana = Objects.requireNonNull(response.getBody())
                .getCards()
                .stream()
                .filter(card -> "minor".equals(card.getType()))
                .toList();

        model.addAttribute("minorArcana", minorArcana);

        return "minor-arcana";
    }

    @ModelAttribute("minorArcana")
    public List<TarotCard> feedMinorArcana() {
        RestTemplate restTemplate = new RestTemplate(); // API-Anfrage
        String endpoint = "https://tarotapi.dev/api/v1/cards";
        TarotCardWrapper response = restTemplate.getForObject(endpoint, TarotCardWrapper.class);

        if (response != null && response.getCards() != null) {
            return response.getCards().stream()
                    .filter(card -> "minor".equals(card.getType()))
                    .toList();
        }

        return List.of();
    }


}
