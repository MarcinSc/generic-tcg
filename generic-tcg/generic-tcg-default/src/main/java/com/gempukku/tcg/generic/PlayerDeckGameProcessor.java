package com.gempukku.tcg.generic;

import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;

import java.util.Map;
import java.util.Set;

public interface PlayerDeckGameProcessor extends GameProcessor {
    public void startProcessing(GameObjects gameObjects, DigitalEnvironment environment, Map<String, GameDeck> gameDeckMap);
    public void startProcessingLoaded(GameObjects gameObjects, DigitalEnvironment environment, Set<String> players);
}
