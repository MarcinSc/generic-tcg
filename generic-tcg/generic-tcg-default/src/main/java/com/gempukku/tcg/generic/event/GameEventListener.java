package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameState;

public interface GameEventListener {
    public void processGameEvent(GameState gameState, GameEvent gameEvent);
}
