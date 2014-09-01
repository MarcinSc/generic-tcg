package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;

/**
 * GameAction consists of multiple successive GameEffects. It contains a context, which is specific for this one action.
 * It allows to execute one GameEffect at a time and query if there is a next one.
 */
public interface GameAction {
    public void processNextGameEffect(GameObjects gameState);

    public boolean hasNextGameEffect(GameObjects gameState);
}
