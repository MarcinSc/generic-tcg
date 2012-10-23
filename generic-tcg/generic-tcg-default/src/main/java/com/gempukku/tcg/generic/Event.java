package com.gempukku.tcg.generic;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.actions.EventAction;

public interface Event {
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback);
}
