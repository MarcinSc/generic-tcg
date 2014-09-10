package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

public class NotCondition implements ActionCondition {
    private ActionCondition _condition;

    public void setCondition(ActionCondition condition) {
        _condition = condition;
    }

    @Override
    public boolean isMet(GameObjects gameObjects, GameEffectContext context) {
        return !_condition.isMet(gameObjects, context);
    }
}
