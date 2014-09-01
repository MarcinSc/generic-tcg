package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;

public interface DecisionHolder {
    public void setDecision(GameObjects gameState, String player, AwaitingDecision decision);

    public AwaitingDecision getDecision(GameObjects gameState, String player);

    public AwaitingDecision removeDecision(GameObjects gameState, String player);

    public boolean hasDecision(GameObjects gameState, String player);

    public boolean hasDecisions(GameObjects gameState);
}
