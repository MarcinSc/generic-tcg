package com.gempukku.tcg;

public interface GameProcessor {
    public void processGameStateUntilInteractionRequired(GameState gameState);

    public void playerSentDecision(GameState gameState, String player, String decision);
}
