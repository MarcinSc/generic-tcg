package com.gempukku.tcg.generic.events;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.Action;
import com.gempukku.tcg.generic.actions.EventAction;

public class StackActionEvent implements Event {
    private Action _action;

    public void setAction(Action action) {
        _action = action;
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        actionStack.stackAction(_action);
    }
}
