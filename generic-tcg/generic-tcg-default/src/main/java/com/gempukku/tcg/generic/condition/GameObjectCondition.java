package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.action.GameActionContext;
import com.gempukku.tcg.GameObjects;

public interface GameObjectCondition {
    public boolean isMet(GameObjects gameObjects, GameActionContext context);
}
