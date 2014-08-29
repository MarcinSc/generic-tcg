package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectEventSource {
    public GameEvent createGameEvent(GameState gameState, DigitalObject digitalObject);
}
