package com.gempukku.tcg.generic;

import com.gempukku.tcg.decision.DecisionHolder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;

import java.util.Map;
import java.util.Set;

public interface PlayerDeckGameProcessor extends GameProcessor {
    public void startProcessing(GameObjects gameObjects, DigitalEnvironment digitalEnvironment, DecisionHolder decisionHolder, Map<String, GameDeck> gameDeckMap);
    public void startProcessingLoaded(GameObjects gameObjects, DigitalEnvironment environment, DecisionHolder decisionHolder, Set<String> players);
}
