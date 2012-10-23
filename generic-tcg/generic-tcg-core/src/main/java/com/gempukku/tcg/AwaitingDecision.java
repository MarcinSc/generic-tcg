package com.gempukku.tcg;

public interface AwaitingDecision {
    public void playerDecided(String answer) throws InvalidDecisionException;
    public String getDecisionString();
}
