package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.generic.PerPlayerObjectImpl;

public class PerPlayerDecisionHolder extends PerPlayerObjectImpl<DecisionHolder> {
    @Override
    protected DecisionHolder createInitialObject(String player) {
        return new DecisionHolder();
    }
}
