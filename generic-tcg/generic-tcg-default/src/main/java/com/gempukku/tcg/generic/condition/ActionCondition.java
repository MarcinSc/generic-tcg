package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

public interface ActionCondition {
    public boolean isMet(GameObjects gameObjects, GameEffectContext context);
}
