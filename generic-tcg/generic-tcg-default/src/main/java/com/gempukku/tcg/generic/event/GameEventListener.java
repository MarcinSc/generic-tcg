package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameObjects;

public interface GameEventListener {
    public void processGameEvent(GameObjects gameState, GameEvent gameEvent);
}
