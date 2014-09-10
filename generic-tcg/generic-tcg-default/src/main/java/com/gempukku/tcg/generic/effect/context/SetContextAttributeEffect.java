package com.gempukku.tcg.generic.effect.context;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class SetContextAttributeEffect implements GameEffect {
    private StringEvaluator _name;
    private StringEvaluator _value;

    public void setName(StringEvaluator name) {
        _name = name;
    }

    public void setValue(StringEvaluator value) {
        _value = value;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final String name = _name.getValue(gameObjects, context);
        final String value = _value.getValue(gameObjects, context);
        context.setAttribute(name, value);
        return Result.pass();
    }
}
