package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;

/**
 * GameAction consists of multiple successive GameEffects. It contains a context, which is specific for this one action.
 * It allows to execute one GameEffect at a time and query if there is a next one.
 */
public interface GameAction {
    public void processNextGameEffect(GameState gameState);

    public boolean hasNextGameEffect(GameState gameState);
}
