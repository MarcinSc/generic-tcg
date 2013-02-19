package com.gempukku.tcg;

import java.util.Set;

public class GameEventEngine {
    private Set<GameEventListener> _listeners;

    public void setGameEventListeners(Set<GameEventListener> gameEventListeners) {
        _listeners = gameEventListeners;
    }

    public void emitGameEvent(GameEvent gameEvent) {
        for (GameEventListener listener : _listeners)
            listener.processGameEvent(gameEvent);
    }
}
