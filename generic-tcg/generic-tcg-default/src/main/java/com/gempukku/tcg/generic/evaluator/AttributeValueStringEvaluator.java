package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.action.GameActionContext;
import com.gempukku.tcg.GameObjects;

public class AttributeValueStringEvaluator implements StringEvaluator {
    private StringEvaluator _propertyName;

    public void setPropertyName(StringEvaluator propertyName) {
        _propertyName = propertyName;
    }

    @Override
    public String getValue(GameObjects gameObjects, GameActionContext context) {
        return context.getValue(_propertyName.getValue(gameObjects, context));
    }
}
