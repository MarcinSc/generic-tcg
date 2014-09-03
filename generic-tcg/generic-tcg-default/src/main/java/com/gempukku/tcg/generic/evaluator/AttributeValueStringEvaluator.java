package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

public class AttributeValueStringEvaluator implements StringEvaluator {
    private StringEvaluator _propertyName;

    public void setPropertyName(StringEvaluator propertyName) {
        _propertyName = propertyName;
    }

    @Override
    public String getValue(GameObjects gameObjects, GameActionContext context) {
        return context.getAttribute(_propertyName.getValue(gameObjects, context));
    }
}
