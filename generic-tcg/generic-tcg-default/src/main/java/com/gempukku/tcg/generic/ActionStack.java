package com.gempukku.tcg.generic;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.actions.Action;

public interface ActionStack {
    void stackAction(Action action);

    void processNextStep(GameState gameState, DecisionCallback decisionCallback);
}
