package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class AttributeLessThanCondition implements ActionCondition {
    private StringEvaluator _name;
    private IntEvaluator _value;

    public void setName(StringEvaluator name) {
        _name = name;
    }

    public void setValue(IntEvaluator value) {
        _value = value;
    }

    @Override
    public boolean isMet(GameObjects gameObjects, GameEffectContext context) {
        final String attributeName = _name.getValue(gameObjects, context);
        final int compareTo = _value.getIntValue(gameObjects, context);
        return Integer.parseInt(context.getAttribute(attributeName)) < compareTo;
    }
}
