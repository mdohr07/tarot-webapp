package com.mblip.tarotwebapp.model;

import java.util.List;

public class TarotApiResponse {
    private List<TarotCard> cards;

    // Getter & Setter
    public List<TarotCard> getCards() {
        return cards;
    }

    public void setCards(List<TarotCard> cards) {
        this.cards = cards;
    }
}

