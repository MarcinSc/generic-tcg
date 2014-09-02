package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;

public interface GameObjectCondition {
    public boolean isMet(GameObjects gameObjects, GameActionContext context);
}
