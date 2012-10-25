package com.gempukku.tcg.generic.events;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.EventAction;

import java.util.Map;

public class SetPlayerObjectsEvent implements Event {
    private String _playerObjectName;
    private Map<String, ?> _values;

    public SetPlayerObjectsEvent(String playerObjectName, Map<String, ?> values) {
        _playerObjectName = playerObjectName;
        _values = values;
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        for (Map.Entry<String, ?> playerValue :_values.entrySet())
            gameState.setPlayerObject(playerValue.getKey(), _playerObjectName, playerValue.getValue());
    }
}
