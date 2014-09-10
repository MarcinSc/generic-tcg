package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class StackResolveEffect implements GameEffect {
    private StringEvaluator _zone;

    public void setZone(StringEvaluator zone) {
        _zone = zone;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        String zoneName = _zone.getValue(gameObjects, context);

        return null;
    }
}
