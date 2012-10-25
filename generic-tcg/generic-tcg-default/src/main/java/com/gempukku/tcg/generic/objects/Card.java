package com.gempukku.tcg.generic.objects;

import java.util.HashMap;
import java.util.Map;

public class Card {
    private String _cardId;
    private Map<String, String> _properties = new HashMap<String, String>();

    public Card(String cardId) {
        _cardId = cardId;
    }

    public String getCardId() {
        return _cardId;
    }

    public void setCardId(String cardId) {
        _cardId = cardId;
    }

    public void setProperty(String name, String value) {
        _properties.put(name, value);
    }

    public String getProperty(String name) {
        return _properties.get(name);
    }
}
