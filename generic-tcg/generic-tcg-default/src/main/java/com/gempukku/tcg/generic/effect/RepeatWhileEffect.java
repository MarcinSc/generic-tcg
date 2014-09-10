package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
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
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        int indexToExecute = getEffectIndex(gameObjects, context);

        while (_condition.isMet(gameObjects, context)) {
            final GameEffect.Result result = _gameObjectEffects.get(indexToExecute).execute(gameObjects, context);

            if (!result._shouldContinue) {
                indexToExecute++;
                if (indexToExecute == _gameObjectEffects.size())
                    indexToExecute = 0;
            }

            if (result._decisions != null) {
                setEffectIndex(gameObjects, context, indexToExecute);
                return result;
            }
        }

        removeEffectIndex(gameObjects, context);
        return Result.pass();
    }

    private int getEffectIndex(GameObjects gameObjects, GameEffectContext context) {
        final String effectIndexStr = context.getAttribute(_indexAttribute.getValue(gameObjects, context));
        int effectIndex = 0;
        if (effectIndexStr != null)
            effectIndex = Integer.parseInt(effectIndexStr);
        return effectIndex;
    }

    private void setEffectIndex(GameObjects gameObjects, GameEffectContext context, int value) {
        context.setAttribute(_indexAttribute.getValue(gameObjects, context), String.valueOf(value));
    }

    private void removeEffectIndex(GameObjects gameObjects, GameEffectContext context) {
        context.removeAttribute(_indexAttribute.getValue(gameObjects, context));
    }
}
