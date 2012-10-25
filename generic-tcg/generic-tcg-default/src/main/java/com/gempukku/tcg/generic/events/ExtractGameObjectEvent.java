package com.gempukku.tcg.generic.events;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.EventAction;

public abstract class ExtractGameObjectEvent implements Event {
    private String _objectName;

    public ExtractGameObjectEvent(String objectName) {
        _objectName = objectName;
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        Object gameObject = gameState.getGameObject(_objectName);
        objectExtracted(gameObject, action, actionStack, gameState, decisionCallback);
    }

    protected abstract void objectExtracted(Object object, EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback);
}
