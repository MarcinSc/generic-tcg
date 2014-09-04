package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class SetActionAttributeEffect implements GameEffect {
    private StringEvaluator _name;
    private StringEvaluator _value;

    public void setName(StringEvaluator name) {
        _name = name;
    }

    public void setValue(StringEvaluator value) {
        _value = value;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        final String name = _name.getValue(gameObjects, context);
        final String value = _value.getValue(gameObjects, context);
        context.setAttribute(name, value);
        return new Result(null, false);
    }
}
