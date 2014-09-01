package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;

import java.util.Map;

public interface PlayerDeckGameProcessor extends GameProcessor {
    public void startProcessing(GameObjects gameObjects, Map<String, GameDeck> gameDeckMap);
    public void startProcessingLoaded(GameObjects gameObjects, DigitalEnvironment environment);
}
