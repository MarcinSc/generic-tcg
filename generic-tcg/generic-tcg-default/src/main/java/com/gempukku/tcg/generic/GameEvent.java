package com.gempukku.tcg.generic;

public class GameEvent {
    private String _type;

    public GameEvent(String type) {
        _type = type;
    }

    public String getType() {
        return _type;
    }
}
