package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.Map;

/**
 * GameAction consists of multiple successive GameEffects. It contains a context, which is specific for this one action.
 * It allows to execute one GameEffect at a time and query if there is a next one.
 */
public interface GameAction {
    public Map<String, AwaitingDecision> processNextGameEffect(GameObjects gameObjects, GameActionContext context);

    public boolean hasNextGameEffect(GameObjects gameObjects, GameActionContext context);
}
