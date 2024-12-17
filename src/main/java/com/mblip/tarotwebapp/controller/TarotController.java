package com.mblip.tarotwebapp.controller;

import com.mblip.tarotwebapp.model.TarotApiResponse;
import com.mblip.tarotwebapp.model.TarotCard;
import com.mblip.tarotwebapp.model.TarotCardWrapper;
import com.mblip.tarotwebapp.service.TarotService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

// Für Eigenes JSON (customMeaningUp.json)
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

@Controller
public class TarotController {

    private static final String BASE_URL = "https://tarotapi.dev/api/v1/cards";


    //     All Cards Page
    @GetMapping("/cards/card-list")
    public String getAllCards(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        if (response != null && response.getCards() != null) {
            model.addAttribute("cards", response.getCards());
        } else {
            model.addAttribute("cards", List.of());
        }
        return "card-list";
    }

    // Major Arcana page
    @GetMapping("/cards/major-arcana")
    public String showMajorArcana(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TarotApiResponse> response = restTemplate.getForEntity(BASE_URL, TarotApiResponse.class);

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
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        if (response != null && response.getCards() != null) {
            /* listet alle Karten aus der API-Antwort, .stream() wandelt die Liste in einen Stream um */
            return response.getCards().stream()
                    .filter(card -> "major".equals(card.getType())) // Karten-Typ "major"
                    .toList(); // Wandelt den gefilterten Stream zurück in eine Liste
        }

        return List.of(); // Leere Liste, falls keine Karten verfügbar sind
    }

    // Minor Arcana page
    @GetMapping("/cards/minor-arcana")
    public String showMinorArcana(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TarotApiResponse> response = restTemplate.getForEntity(BASE_URL, TarotApiResponse.class);

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
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        if (response != null && response.getCards() != null) {
            return response.getCards().stream()
                    .filter(card -> "minor".equals(card.getType()))
                    .toList();
        }

        return List.of();
    }

    // Card Details
    @GetMapping("/cards/{nameShort}")
    public String showCardDetails(@PathVariable String nameShort, Model model) {
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        if (response != null && response.getNhits() > 0) {
            // Suche nach Karte basierend auf name_short
            TarotCard tarotCard = response.getCards().stream()
                    .filter(card -> card.getNameShort().equalsIgnoreCase(nameShort))  // Suche nach name_short
                    .findFirst()
                    .orElse(null);

            if (tarotCard != null) {
                model.addAttribute("tarotCard", tarotCard);
                return "card-details";  // Zeigt die Karte an
            }
        }

        // Falls die Karte nicht gefunden wurde, zurück zur Liste
        return "redirect:/all-cards/card-list";
    }

    // Draw three cards
    @GetMapping("/draw-three-cards")
    @ResponseBody
    public List<TarotCard> drawThreeCards() {
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        if (response != null && response.getNhits() > 0) {
            List<TarotCard> allCards = response.getCards();
            List<TarotCard> threeCards = TarotService.drawThreeCards(allCards);

            // CustomMeaningUp für jede Karte hinzufügen
            List<TarotCard> localCards = loadLocalCards();
            threeCards.forEach(card -> localCards.stream()
                    .filter(localCard -> localCard.getName() != null && localCard.getName().equals(card.getName()))
                    .findFirst()
                    .ifPresent(localCard -> card.setCustomMeaningUp(localCard.getCustomMeaningUp()))
            );

            return threeCards; // Als JSON übergeben
        }
        return List.of();
    }

    // Show three cards
    @GetMapping("/past-present-future")
    public String showPastPresentFuture(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        if (response != null && response.getNhits() > 0) {
            List<TarotCard> allCards = response.getCards();
            List<TarotCard> threeCards = TarotService.drawThreeCards(allCards);

            // customMeaningUp hinzufügen
            List<TarotCard> localCards = loadLocalCards();
            threeCards.forEach(card -> localCards.stream()
                    .filter(localCard -> localCard.getName() != null && localCard.getName().equals(card.getName()))
                    .findFirst()
                    .ifPresent(localCard -> card.setCustomMeaningUp(localCard.getCustomMeaningUp()))
            );
            model.addAttribute("threeCards", threeCards); // Karten ins Model
        } else {
            model.addAttribute("threeCards", List.of()); // Leere Liste
        }
        return "past-present-future";
    }

    // Daily Card
    @GetMapping("/")
    public String showHomePage(Model model) {
        // API-Aufruf
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        List<TarotCard> allCards = (response != null && response.getCards() != null)
                ? response.getCards()
                : List.of();

        // Lokale Daten aus json laden
        List<TarotCard> localCards = loadLocalCards();

        // API-Karten mit lokalen Karten abgleichen oder ergänzen
        allCards.forEach(apiCard -> {
            localCards.stream()
                    .filter(localCard -> localCard.getName() != null && localCard.getName().equals(apiCard.getName()))
                    .findFirst()
                    .ifPresent(localCard -> {
                        System.out.println("Found matching card: " + localCard.getName());
                        apiCard.setCustomMeaningUp(localCard.getCustomMeaningUp());
                    });
        });


        // Zufällige Tageskarte ziehen
        if (!allCards.isEmpty()) {
            TarotCard dailyCard = TarotService.drawDailyCard(allCards);
            model.addAttribute("dailyCard", dailyCard);
        }
        return "index"; // Gibt die index.html zurück
    }

    //Zufällige Karte in JSON
    @GetMapping("/draw-daily-card")
    @ResponseBody
    public TarotCard drawDailyCard() {
        RestTemplate restTemplate = new RestTemplate();
        TarotCardWrapper response = restTemplate.getForObject(BASE_URL, TarotCardWrapper.class);

        if (response != null && response.getNhits() > 0) {
            List<TarotCard> allCards = response.getCards();
            TarotCard dailyCard = TarotService.drawDailyCard(allCards);

            // customMeaningUp aus lokalem json hinzufügen
            List<TarotCard> localCards = loadLocalCards();
            localCards.stream()
                    .filter(localCard -> localCard.getName() != null && localCard.getName().equals(dailyCard.getName()))
                    .findFirst()
                    .ifPresent(localCard -> {
                        dailyCard.setCustomMeaningUp(localCard.getCustomMeaningUp());
                    });

            dailyCard.setImageUrl("/img/RiderWaiteTarotImages/" + dailyCard.getNameShort() + ".jpg");
            return dailyCard; // Automatisch in JSON umgewandelt
        }

        System.out.println("API Response: " + response);
        return null;
    }

    // Custom Meaning für Tageskarte laden
    private List<TarotCard> loadLocalCards() {
        try {
            // Ressource im Klassenpfad laden
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/json/customMeaningUp.json");

            // Falls die Ressource nicht gefunden wurde
            if (inputStream == null) {
                throw new FileNotFoundException("customMeaningUp.json nicht gefunden!");
            }

            // JSON-Datei lesen
            String jsonContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            // JSON in Objekt umwandeln
            ObjectMapper objectMapper = new ObjectMapper();
            TarotCardWrapper wrapper = objectMapper.readValue(jsonContent, TarotCardWrapper.class);

            return wrapper.getCards();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();  // Rückgabe einer leeren Liste bei Fehler
        }
    }

}