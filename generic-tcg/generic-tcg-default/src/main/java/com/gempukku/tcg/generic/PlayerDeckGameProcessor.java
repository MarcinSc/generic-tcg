package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.digital.DigitalEnvironment;

import java.util.Map;
import java.util.Set;

public interface PlayerDeckGameProcessor extends GameProcessor {
    public void startProcessingNewGame(GameObjects gameObjects, DigitalEnvironment environment, Map<String, GameDeck> gameDeckMap);
    public void startProcessingLoadedGame(GameObjects gameObjects, DigitalEnvironment environment, Set<String> players);
}
