package com.gempukku.tcg.generic.event.condition;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameEventCondition;

public class EventTypeCondition implements GameEventCondition {
    private String _type;

    public void setType(String type) {
        _type = type;
    }

    @Override
    public boolean matches(GameState gameState, GameEvent event) {
        return event.getType().equals(_type);
    }
}
