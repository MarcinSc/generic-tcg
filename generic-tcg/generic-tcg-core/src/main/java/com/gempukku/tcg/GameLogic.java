package com.gempukku.tcg;

public interface GameLogic {
    public void proceed(GameState gameState, DecisionCallback decisionCallback);
    public boolean isFinished(GameState gameState, DecisionCallback decisionCallback);
}
