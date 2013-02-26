package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface GameObjectEventSource {
    public GameEvent createGameEvent(GameState gameState, GameObject gameObject);
}
