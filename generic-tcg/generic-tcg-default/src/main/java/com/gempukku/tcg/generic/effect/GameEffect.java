package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameActionContext;

/**
 * GameEffect executes a smallest atomic action possible on the GameState.
 */
public interface GameEffect {
    public void executeGameEffect(GameState gameState, GameActionContext gameActionContext);
}
