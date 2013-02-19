package com.gempukku.tcg.generic.event;

public class GameEvent {
    private String _type;

    public GameEvent(String type) {
        _type = type;
    }

    public String getType() {
        return _type;
    }
}
