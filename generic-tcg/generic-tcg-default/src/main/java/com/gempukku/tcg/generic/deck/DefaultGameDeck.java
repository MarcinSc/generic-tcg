package com.gempukku.tcg.generic.deck;

import com.gempukku.tcg.GameDeck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
