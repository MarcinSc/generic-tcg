package com.gempukku.tcg;

public interface GameProcessor {
    public void playerSentDecision(GameState gameState, String player, String decision);
}
