package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.condition.ActionCondition;

public class ConditionalEffect implements GameEffect {
    private ActionCondition _condition;
    private GameEffect _effect;
    private GameEffect _elseEffect;

    public void setCondition(ActionCondition gameObjectCondition) {
        _condition = gameObjectCondition;
    }

    public void setEffect(GameEffect effect) {
        _effect = effect;
    }

    public void setElseEffect(GameEffect elseEffect) {
        _elseEffect = elseEffect;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        if (_condition.isMet(gameObjects, context))
            return _effect.execute(gameObjects, context);
        else if (_elseEffect != null)
            return _elseEffect.execute(gameObjects, context);
        else
            return Result.pass();
    }
}
