package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameObjects;

import java.util.Set;

public class GameEventEngine {
    private Set<GameEventListener> _listeners;

    public void setGameEventListeners(Set<GameEventListener> gameEventListeners) {
        _listeners = gameEventListeners;
    }

    public void emitGameEvent(GameObjects gameState, GameEvent gameEvent) {
        for (GameEventListener listener : _listeners)
            listener.processGameEvent(gameState, gameEvent);
    }
}
