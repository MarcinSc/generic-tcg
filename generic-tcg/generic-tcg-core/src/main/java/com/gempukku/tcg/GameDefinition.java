package com.gempukku.tcg;

import java.util.Map;

public interface GameDefinition<T> {
    public GameLogic createGameLogic(Map<String, String> parameters, Map<String, Deck> playerDecks);

    public GameStateEvaluator<T> createGameStateEvaluator();

    public GameStateObserverFactory<T> createGameStateObserverFactory();
}
