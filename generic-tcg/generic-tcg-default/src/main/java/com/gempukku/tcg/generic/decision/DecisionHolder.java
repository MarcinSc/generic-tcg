package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameState;

public interface DecisionHolder {
    public void setDecision(GameState gameState, String player, AwaitingDecision decision);

    public AwaitingDecision getDecision(GameState gameState, String player);

    public AwaitingDecision removeDecision(GameState gameState, String player);

    public boolean hasDecision(GameState gameState, String player);

    public boolean hasDecisions(GameState gameState);
}
