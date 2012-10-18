package com.gempukku.tcg.generic;

public interface DecisionCallback {
    public void sendDecision(String player, AwaitingDecision awaitingDecision);
}
