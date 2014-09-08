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
        final int effectIndex = getEffectIndex(gameObjects, context);
        final GameEffect gameEffect = _effects.get(effectIndex);
        final Result result = gameEffect.execute(gameObjects, context);
        if (!result._shouldContinue) {
            if (effectIndex + 1 < _effects.size()) {
                setEffectIndex(gameObjects, context, effectIndex + 1);
                return new Result(null, true);
            } else {
                removeEffectIndex(gameObjects, context);
                return new Result(null, false);
            }
        }

        return new Result(result._decisions, true);
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
