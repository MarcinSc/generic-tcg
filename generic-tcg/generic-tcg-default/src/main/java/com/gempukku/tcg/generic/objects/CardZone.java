package com.gempukku.tcg.generic.objects;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class CardZone {
    private Set<Card> _cards = new LinkedHashSet<Card>();

    public void addCard(Card card) {
        _cards.add(card);
    }

    public Iterator<Card> iterateCards() {
        return _cards.iterator();
    }

    public Card getCard(String cardId) {
        for (Card card : _cards) {
            if (card.getCardId().equals(cardId))
                return card;
        }
        return null;
    }

    public boolean removeCard(String cardId) {
        Iterator<Card> cardIterator = iterateCards();
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            if (card.getCardId().equals(cardId)) {
                cardIterator.remove();
                return true;
            }
        }
        return false;
    }
}
