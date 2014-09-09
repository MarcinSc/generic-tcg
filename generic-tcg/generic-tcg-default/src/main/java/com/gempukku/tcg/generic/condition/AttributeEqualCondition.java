package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class AttributeEqualCondition implements ActionCondition {
    private StringEvaluator _name;
    private StringEvaluator _value;

    public void setName(StringEvaluator name) {
        _name = name;
    }

    public void setValue(StringEvaluator value) {
        _value = value;
    }

    @Override
    public boolean isMet(GameObjects gameObjects, GameActionContext context) {
        String name = _name.getValue(gameObjects, context);
        String value = _value.getValue(gameObjects, context);
        return value.equals(context.getAttribute(name));
    }
}
