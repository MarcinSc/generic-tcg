package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;

public interface DecisionHolder {
    public void setDecision(GameObjects gameObjects, String player, AwaitingDecision decision);

    public AwaitingDecision getDecision(GameObjects gameObjects, String player);

    public AwaitingDecision removeDecision(GameObjects gameObjects, String player);

    public boolean hasDecision(GameObjects gameObjects, String player);

    public boolean hasDecisions(GameObjects gameObjects);
}
