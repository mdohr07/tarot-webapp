package com.mblip.tarotwebapp.model;

import java.util.List;

public class TarotCardWrapper {
    private int nhits; // Anzahl der Treffer
    private List<TarotCard> cards; // Die Kartenliste

    // Getter & Setter
    public int getNhits() {
        return nhits;
    }

    public void setNhits(int nhits) {
        this.nhits = nhits;
    }

    public List<TarotCard> getCards() {
        return cards;
    }

    public void setCards(List<TarotCard> cards) {
        this.cards = cards;
    }

}
