package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameObjectEventSource;

public class EmitEventEffect extends DigitalObjectEffect {
    private GameObjectEventSource _eventSource;

    public void setEventSource(GameObjectEventSource eventSource) {
        _eventSource = eventSource;
    }

    @Override
    public void executeGameEffect(GameObjects gameState, DigitalObject gameObjectSource) {
        GameEvent gameEvent = createGameEvent(gameState, gameObjectSource);
        GenericContextObjects.extractGameObject(gameState, GenericContextObjects.GAME_EVENT_ENGINE).emitGameEvent(gameState, gameEvent);
    }

    private GameEvent createGameEvent(GameObjects gameState, DigitalObject gameObjectSource) {
        return _eventSource.createGameEvent(gameState, gameObjectSource);
    }
}
