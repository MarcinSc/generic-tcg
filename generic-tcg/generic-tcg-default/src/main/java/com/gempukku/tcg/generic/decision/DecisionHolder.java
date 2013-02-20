package com.gempukku.tcg.generic.decision;

public class DecisionHolder {
    private AwaitingDecision _decision;

    public void setDecision(AwaitingDecision decision) {
        _decision = decision;
    }

    public AwaitingDecision removeDecision() {
        AwaitingDecision result = _decision;
        _decision = null;
        return result;
    }

    public AwaitingDecision getDecision() {
        return _decision;
    }
}
