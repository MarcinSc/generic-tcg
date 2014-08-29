package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.event.GameObjectEventSource;

public class EmitEventEffect extends DigitalObjectEffect {
    private String _gameEventEngine;
    private GameObjectEventSource _eventSource;

    public void setGameEventEngine(String gameEventEngine) {
        _gameEventEngine = gameEventEngine;
    }

    public void setEventSource(GameObjectEventSource eventSource) {
        _eventSource = eventSource;
    }

    @Override
    public void executeGameEffect(GameState gameState, DigitalObject gameObjectSource) {
        GameEvent gameEvent = createGameEvent(gameState, gameObjectSource);
        ((GameEventEngine) gameState.getGameObject(_gameEventEngine)).emitGameEvent(gameState, gameEvent);
    }

    private GameEvent createGameEvent(GameState gameState, DigitalObject gameObjectSource) {
        return _eventSource.createGameEvent(gameState, gameObjectSource);
    }
}
