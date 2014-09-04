package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.condition.ActionCondition;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.List;

public class RepeatWhileEffect implements GameEffect {
    private ActionCondition _condition;
    private StringEvaluator _indexAttribute = new ConstantStringEvaluator("repeatWhileEffectIndex");
    private List<? extends GameEffect> _gameObjectEffects;

    public void setCondition(ActionCondition condition) {
        _condition = condition;
    }

    public void setEffects(List<? extends GameEffect> gameObjectEffects) {
        _gameObjectEffects = gameObjectEffects;
    }

    public void setIndexAttribute(StringEvaluator indexAttribute) {
        _indexAttribute = indexAttribute;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        if (!_condition.isMet(gameObjects, context)) {
            removeEffectIndex(gameObjects, context);
            return new Result(null, false);
        }

        int indexToExecute = getEffectIndex(gameObjects, context);
        final GameEffect.Result result = _gameObjectEffects.get(indexToExecute).execute(gameObjects, context);

        if (!result._shouldContinue)
            if (indexToExecute + 1 < _gameObjectEffects.size())
                setEffectIndex(gameObjects, context, indexToExecute + 1);
            else
                setEffectIndex(gameObjects, context, 0);

        final boolean repeat = _condition.isMet(gameObjects, context);
        if (!repeat)
            removeEffectIndex(gameObjects, context);
        return new Result(result._decisions, repeat);
    }

    private int getEffectIndex(GameObjects gameObjects, GameActionContext context) {
        final String effectIndexStr = context.getAttribute(_indexAttribute.getValue(gameObjects, context));
        int effectIndex = 0;
        if (effectIndexStr != null)
            effectIndex = Integer.parseInt(effectIndexStr);
        return effectIndex;
    }

    private void setEffectIndex(GameObjects gameObjects, GameActionContext context, int value) {
        context.setAttribute(_indexAttribute.getValue(gameObjects, context), String.valueOf(value));
    }

    private void removeEffectIndex(GameObjects gameObjects, GameActionContext context) {
        context.removeAttribute(_indexAttribute.getValue(gameObjects, context));
    }
}
