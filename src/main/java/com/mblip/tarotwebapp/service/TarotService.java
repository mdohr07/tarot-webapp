package com.mblip.tarotwebapp.service;

import com.mblip.tarotwebapp.model.TarotCard;

import java.util.List;
import java.util.Random;

public class TarotService {

    // TODO: Tageskarte
    // TODO: 3 Karten-Legung

    // Draw a random Card (daily card)
    public static TarotCard drawDailyCard(List<TarotCard> cardList) {
        Random random = new Random();
        int index = random.nextInt(cardList.size()); // Zufälliger Index
        return cardList.get(index); // Gibt die Karte an diesem Index zurück

    }
}
