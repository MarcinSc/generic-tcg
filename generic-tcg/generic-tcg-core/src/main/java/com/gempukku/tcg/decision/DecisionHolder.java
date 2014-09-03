package com.gempukku.tcg.decision;

public interface DecisionHolder {
    public void setDecision(String player, AwaitingDecision decision);

    public AwaitingDecision getDecision(String player);

    public AwaitingDecision removeDecision(String player);

    public boolean hasDecision(String player);

    public boolean hasDecisions();
}
