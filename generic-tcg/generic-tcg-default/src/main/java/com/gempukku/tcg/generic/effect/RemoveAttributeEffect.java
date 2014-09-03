package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class RemoveAttributeEffect implements GameEffect {
    private StringEvaluator _attributeName;

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        final String attributeName = _attributeName.getValue(gameObjects, context);
        context.removeAttribute(attributeName);
        
        return new Result(null, false);
    }
}
