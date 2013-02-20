package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;

import java.util.Map;

public interface PlayerDeckGameProcessor extends GameProcessor {
    public void startProcessing(GameState gameState, Map<String, GameDeck> gameDeckMap);
}
