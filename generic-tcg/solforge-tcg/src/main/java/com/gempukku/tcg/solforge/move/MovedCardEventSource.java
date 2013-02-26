package com.gempukku.tcg.solforge.move;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameObjectEventSource;
import com.gempukku.tcg.generic.object.GameObject;

public class MovedCardEventSource implements GameObjectEventSource {
    @Override
    public GameEvent createGameEvent(GameState gameState, GameObject gameObject) {
        return new MoveEvent(gameObject);
    }
}
