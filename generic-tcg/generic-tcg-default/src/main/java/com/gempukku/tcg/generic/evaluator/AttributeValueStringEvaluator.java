package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

public class AttributeValueStringEvaluator implements StringEvaluator {
    private StringEvaluator _propertyName;

    public void setPropertyName(StringEvaluator propertyName) {
        _propertyName = propertyName;
    }

    @Override
    public String getValue(GameObjects gameObjects, GameEffectContext context) {
        return context.getAttribute(_propertyName.getValue(gameObjects, context));
    }
}
