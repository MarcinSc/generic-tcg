package com.gempukku.tcg;

import java.util.*;

public class DefaultGameDeck implements GameDeck {
    private Map<String, List<String>> _cardBlueprints = new HashMap<String, List<String>>();

    public void addDeckPart(String type, List<String> cardBlueprints) {
        _cardBlueprints.put(type, Collections.unmodifiableList(new ArrayList<String>(cardBlueprints)));
    }

    @Override
    public Map<String, List<String>> getCardBlueprints() {
        return Collections.unmodifiableMap(_cardBlueprints);
    }
}
