package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

public interface IntEvaluator extends StringEvaluator {
    public int getIntValue(GameObjects gameObjects, GameEffectContext context);
}
