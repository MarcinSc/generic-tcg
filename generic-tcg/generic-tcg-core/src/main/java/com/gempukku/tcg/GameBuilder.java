package com.gempukku.tcg;

import com.gempukku.tcg.decision.DecisionHolder;

public interface GameBuilder {
    public GameObjects getGameObjects();

    public GameProcessor getGameProcessor();

    public DecisionHolder getDecisionHolder();
}
