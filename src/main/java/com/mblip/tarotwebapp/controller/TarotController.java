package com.mblip.tarotwebapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mblip.tarotwebapp.model.TarotCard;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TarotController {
    // TODO: Mapping
    // TODO: List<TarotCard> getAllCards()
    // TODO: TarotCard getDailyCard()
    // TODO: List<TarotCard> getThreeCards()

    @GetMapping("/cards")
    public String cards(Model model) {
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

        return "cards";
    }

 }
