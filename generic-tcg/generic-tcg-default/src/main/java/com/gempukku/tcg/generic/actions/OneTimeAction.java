package com.gempukku.tcg.generic.actions;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;

public abstract class OneTimeAction implements Action {
    private boolean _processed;

    @Override
    public Action getNextAction(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        return null;
    }

    @Override
    public void processNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        processAction(actionStack, gameState, decisionCallback);
        _processed = true;
    }

    protected abstract void processAction(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback);

    @Override
    public boolean hasNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        return !_processed;
    }
}
