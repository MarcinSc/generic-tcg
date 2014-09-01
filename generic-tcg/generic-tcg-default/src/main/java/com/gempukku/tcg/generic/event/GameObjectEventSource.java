package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectEventSource {
    public GameEvent createGameEvent(GameObjects gameState, DigitalObject digitalObject);
}
