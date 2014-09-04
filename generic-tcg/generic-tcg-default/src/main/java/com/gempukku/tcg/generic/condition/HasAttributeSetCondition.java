package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class HasAttributeSetCondition implements ActionCondition {
    private StringEvaluator _name;

    public void setName(StringEvaluator name) {
        _name = name;
    }

    @Override
    public boolean isMet(GameObjects gameObjects, GameActionContext context) {
        final String name = _name.getValue(gameObjects, context);
        return context.getAttribute(name) != null;
    }
}
