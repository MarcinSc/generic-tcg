package com.gempukku.tcg.generic.event.type;

import com.gempukku.tcg.generic.event.GameEvent;

public class PlayerTurnStartEvent extends GameEvent {
    private String _player;

    public PlayerTurnStartEvent(String player) {
        super(GenericEventType.PLAYER_TURN_START);
        _player = player;
    }

    public String getPlayer() {
        return _player;
    }
}
