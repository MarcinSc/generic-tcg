package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.decision.DecisionHolder;

public class ManagerDecisionHolderFactory implements DecisionHolderFactory {
    @Override
    public DecisionHolder create() {
        return new DecisionHolderManager();
    }
}
