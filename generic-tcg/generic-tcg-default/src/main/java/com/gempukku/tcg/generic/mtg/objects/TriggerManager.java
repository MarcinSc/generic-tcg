package com.gempukku.tcg.generic.mtg.objects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TriggerManager {
    private TriggerContainer _triggerContainer = new TriggerContainer();

    private List<GameEventListener> _gameEventListeners = new LinkedList<GameEventListener>();

    public void setGameEventListeners(Set<GameEventListener> gameEventListeners) {
        _gameEventListeners.addAll(gameEventListeners);
    }

    public void addGameEventListener(GameEventListener gameEventListener) {
        _gameEventListeners.add(gameEventListener);
    }

    public void removeGameEventListener(GameEventListener gameEventListener) {
        _gameEventListeners.remove(gameEventListener);
    }

    public void distributeGameEvent(String type, Map<String, String> params) {
        for (GameEventListener gameEventListener : _gameEventListeners)
            gameEventListener.triggerHappened(_triggerContainer, type, params);
    }

    public Set<Trigger> consumeAwaitingTriggers() {
        return _triggerContainer.consumeWaitingTriggers();
    }
}
