package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

import java.util.List;

public class AndCondition implements ActionCondition {
    private List<? extends ActionCondition> _conditions;

    public void setConditions(List<? extends ActionCondition> conditions) {
        _conditions = conditions;
    }

    @Override
    public boolean isMet(GameObjects gameObjects, GameActionContext context) {
        for (ActionCondition condition : _conditions) {
            if (!condition.isMet(gameObjects, context))
                return false;
        }

        return true;
    }
}
