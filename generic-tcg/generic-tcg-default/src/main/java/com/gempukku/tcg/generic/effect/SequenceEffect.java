package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.List;

public class SequenceEffect implements GameEffect {
    private List<? extends GameEffect> _effects;
    private StringEvaluator _indexAttribute = new ConstantStringEvaluator("sequenceEffectIndex");

    public void setEffects(List<? extends GameEffect> gameObjectEffects) {
        _effects = gameObjectEffects;
    }

    public void setIndexAttribute(StringEvaluator indexAttribute) {
        _indexAttribute = indexAttribute;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        int effectIndex = getEffectIndex(gameObjects, context);
        while (true) {
            final Result result = _effects.get(effectIndex).execute(gameObjects, context);
            if (!result._shouldContinue) {
                effectIndex++;
                if (effectIndex == _effects.size()) {
                    removeEffectIndex(gameObjects, context);
                    return Result.pass();
                }
            } else {
                if (result._decisions != null) {
                    setEffectIndex(gameObjects, context, effectIndex);
                    return result;
                }
            }
        }
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
