package com.gempukku.tcg;

public class GameEvent {
    private String _type;

    public GameEvent(String type) {
        _type = type;
    }

    public String getType() {
        return _type;
    }
}
