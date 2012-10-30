package com.gempukku.tcg;

public interface GameStateEvaluator<T> {
    public void gameStateChanged(GameState gameState, UserFeedback userFeedback);

    public void addGameStateObserver(T gameStateObserver);

    public void removeGameStateObserver(T gameStateObserver);
}
