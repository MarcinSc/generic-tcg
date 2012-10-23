package com.gempukku.tcg.generic.actions;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;

public interface Action {
    public void processNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback);
    public boolean hasNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback);
    public Action getNextAction(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback);
}
