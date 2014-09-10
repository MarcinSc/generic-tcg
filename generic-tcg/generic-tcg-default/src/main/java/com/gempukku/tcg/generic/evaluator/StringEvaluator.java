package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

public interface StringEvaluator {
    public String getValue(GameObjects gameObjects, GameEffectContext context);
}
