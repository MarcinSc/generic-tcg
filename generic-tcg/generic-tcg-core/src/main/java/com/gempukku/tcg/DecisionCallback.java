package com.gempukku.tcg;

public interface DecisionCallback {
    public void sendDecision(String player, AwaitingDecision awaitingDecision);
}
