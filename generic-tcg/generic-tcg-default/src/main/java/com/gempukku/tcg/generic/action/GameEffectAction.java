package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.List;
import java.util.Map;

public class GameEffectAction implements GameAction {
    private List<? extends GameEffect> _gameObjectEffects;
    private StringEvaluator _indexAttribute = new ConstantStringEvaluator("effectIndex");

    public void setEffects(List<? extends GameEffect> gameObjectEffects) {
        _gameObjectEffects = gameObjectEffects;
    }

    public void setIndexAttribute(StringEvaluator indexAttribute) {
        _indexAttribute = indexAttribute;
    }

    @Override
    public boolean hasNextGameEffect(GameObjects gameObjects, GameActionContext context) {
        int effectIndex = getEffectIndex(gameObjects, context);

        return effectIndex < _gameObjectEffects.size();
    }

    @Override
    public Map<String, AwaitingDecision> processNextGameEffect(GameObjects gameObjects, GameActionContext context) {
        int indexToExecute = getEffectIndex(gameObjects, context);
        final GameEffect.Result result = _gameObjectEffects.get(indexToExecute).execute(gameObjects, context);

        if (!result._shouldContinue)
            setEffectIndex(gameObjects, context, indexToExecute + 1);

        return result._decisions;
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
}