package com.gempukku.tcg.generic.decision;

public class ManagerDecisionHolderFactory implements DecisionHolderFactory {
    @Override
    public DecisionHolder create() {
        return new DecisionHolderManager();
    }
}
