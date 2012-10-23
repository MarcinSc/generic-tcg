package com.gempukku.tcg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deck {
    private Map<String, List<String>> _cards = new HashMap<String, List<String>>();

    public void addCards(String zone, List<String> cards) {
        _cards.put(zone, cards);
    }

    public List<String> getCards(String zone) {
        return _cards.get(zone);
    }
}
