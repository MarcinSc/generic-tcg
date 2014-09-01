package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.modifier.GameModifier;

import java.util.List;
import java.util.Map;

public interface PlayerDeckGameProcessor extends GameProcessor {
    public void startProcessing(GameState gameState, Map<String, GameDeck> gameDeckMap);
}
