package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.event.GameObjectEventSource;
import com.gempukku.tcg.generic.object.GameObject;

public class EmitEventEffect extends GameObjectEffect {
    private String _gameEventEngine;
    private GameObjectEventSource _eventSource;

    public void setGameEventEngine(String gameEventEngine) {
        _gameEventEngine = gameEventEngine;
    }

    public void setEventSource(GameObjectEventSource eventSource) {
        _eventSource = eventSource;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObjectSource) {
        GameEvent gameEvent = createGameEvent(gameState, gameObjectSource);
        ((GameEventEngine) gameState.getGameObject(_gameEventEngine)).emitGameEvent(gameState, gameEvent);
    }

    private GameEvent createGameEvent(GameState gameState, GameObject gameObjectSource) {
        return _eventSource.createGameEvent(gameState, gameObjectSource);
    }
}
