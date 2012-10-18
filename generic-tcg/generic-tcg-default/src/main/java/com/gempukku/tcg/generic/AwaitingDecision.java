package com.gempukku.tcg.generic;

import com.gempukku.tcg.InvalidDecisionException;

public interface AwaitingDecision {
    public void playerDecided(String answer) throws InvalidDecisionException;
    public String getDecisionString();
}
