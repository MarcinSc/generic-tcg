package com.gempukku.tcg.generic.events;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.EventAction;

public class SetGameObjectEvent implements Event {
    private String _gameObjectName;
    private Object _value;

    public SetGameObjectEvent(String gameObjectName, Object value) {
        _gameObjectName = gameObjectName;
        _value = value;
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        gameState.setGameObject(_gameObjectName, _value);
    }
}
