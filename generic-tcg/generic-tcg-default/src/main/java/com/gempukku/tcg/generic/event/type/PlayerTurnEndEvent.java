package com.gempukku.tcg.generic.event.type;

import com.gempukku.tcg.generic.event.GameEvent;

public class PlayerTurnEndEvent extends GameEvent {
    private String _player;

    public PlayerTurnEndEvent(String player) {
        super(GenericEventType.PLAYER_TURN_END);
        _player = player;
    }

    public String getPlayer() {
        return _player;
    }
}
