package com.gempukku.tcg.generic.objects;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CardIdManager {
    private Set<String> _usedCardIds = new HashSet<String>();
    private char[] _allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private int _cardIdLength = 5;

    public void addNewCardToZone(CardZone cardZone, Map<String, String> properties) {
        String cardId = generateNewId();
        _usedCardIds.add(cardId);
        Card card =new Card(cardId);
        for (Map.Entry<String, String> cardProperty: properties.entrySet())
            card.setProperty(cardProperty.getKey(), cardProperty.getValue());
        cardZone.addCard(card);
    }

    public String moveCardsBetweenZones(CardZone fromZone, CardZone toZone, String cardId) {
        Card card = fromZone.getCard(cardId);
        if (card == null)
            throw new IllegalArgumentException("Card was not in the fromZone");
        fromZone.removeCard(cardId);
        String newId = generateNewId();
        _usedCardIds.remove(cardId);
        _usedCardIds.add(newId);
        card.setCardId(newId);
        toZone.addCard(card);
        return newId;
    }

    public boolean removeCardFromZone(CardZone fromZone, String cardId) {
        boolean removed = fromZone.removeCard(cardId);
        if (!removed)
            throw new IllegalArgumentException("Card was not in the fromZone");
        return removed;
    }

    private String generateNewId() {
        Random rnd = new Random();
        String newId;
        do {
            char[] idChar = new char[_cardIdLength];
            for (int i=0; i<_cardIdLength; i++)
                idChar[i] = _allowedChars[rnd.nextInt(_allowedChars.length)];
            newId = new String(idChar);
        } while (_usedCardIds.contains(newId));
        return newId;
    }
}
