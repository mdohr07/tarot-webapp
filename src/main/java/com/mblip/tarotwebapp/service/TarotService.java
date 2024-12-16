package com.mblip.tarotwebapp.service;

import com.mblip.tarotwebapp.model.TarotCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TarotService {

    // Draw a random Card (daily card)
    public static TarotCard drawDailyCard(List<TarotCard> cardList) {
        Random random = new Random();
        int index = random.nextInt(cardList.size()); // Zufälliger Index
        return cardList.get(index); // Gibt die Karte an diesem Index zurück
    }

    // Draw three cards
    public static List<TarotCard> drawThreeCards(List<TarotCard> cardList) {
        Random random = new Random();
        List<TarotCard> drawnCards = new ArrayList<>();
        List<TarotCard> tempList = new ArrayList<>(cardList); // Kopie der Liste, um Duplikate zu vermeiden

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(tempList.size());
            drawnCards.add(tempList.get(index));// Karte zur tempList hinzufügen
            tempList.remove(index); // Karte wieder aus der tempList entfernen
        }
        return drawnCards;
    }
}