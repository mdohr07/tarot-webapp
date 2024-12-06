package com.mblip.tarotwebapp.controller;

import com.mblip.tarotwebapp.model.TarotApiResponse;
import com.mblip.tarotwebapp.model.TarotCard;
import com.mblip.tarotwebapp.model.TarotCardWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

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

    //     All Cards Page
    @GetMapping("/all-cards/card-list")
    public String getAllCards(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "https://tarotapi.dev/api/v1/cards";
        TarotCardWrapper response = restTemplate.getForObject(endpoint, TarotCardWrapper.class);

        if (response != null && response.getCards() != null) {
            model.addAttribute("cards", response.getCards());
        } else {
            model.addAttribute("cards", List.of());
        }
        return "card-list";
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

    // Card Details
    @GetMapping("/all-cards/{cardName}")
    public String showCardDetails(@PathVariable String cardName, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint = "https://tarotapi.dev/api/v1/cards";
        TarotCardWrapper response = restTemplate.getForObject(endpoint, TarotCardWrapper.class);

        if (response != null && response.getCards() != null) {
            // Suche nach Karte mit dem passenden Namen
            TarotCard tarotCard = response.getCards()
                    .stream()
                    .filter(card -> card.getName().equalsIgnoreCase(cardName))
                    .findFirst()
                    .orElse(null);

            if (tarotCard != null) {
                model.addAttribute("tarotCard", tarotCard);
                return "card-details";
            }
        }
        // Falls die Karte nicht gefunden wurde
        return "redirect:/all-cards/card-list"; // Zurück zur Liste
    }
}
