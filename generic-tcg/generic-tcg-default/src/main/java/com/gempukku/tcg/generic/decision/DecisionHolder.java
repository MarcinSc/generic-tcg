package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;

public interface DecisionHolder {
    public void setDecision(String player, AwaitingDecision decision);

    public AwaitingDecision getDecision(String player);

    public AwaitingDecision removeDecision(String player);

    public boolean hasDecision(String player);

    public boolean hasDecisions();
}
