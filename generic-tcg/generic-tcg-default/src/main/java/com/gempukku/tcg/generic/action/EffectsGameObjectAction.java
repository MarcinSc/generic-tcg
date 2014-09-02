package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.effect.GameObjectEffectSerie;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EffectsGameObjectAction implements GameAction {
    private List<? extends GameObjectEffectSerie> _gameObjectEffects;
    private StringEvaluator _indexAttribute = new ConstantStringEvaluator("effectIndex");

    public void setEffects(List<? extends GameObjectEffectSerie> gameObjectEffects) {
        _gameObjectEffects = gameObjectEffects;
    }

    public void setIndexAttribute(StringEvaluator indexAttribute) {
        _indexAttribute = indexAttribute;
    }

    @Override
    public boolean hasNextGameEffect(GameObjects gameObjects, DigitalObject context) {
        int effectIndex = getEffectIndex(gameObjects, context);

        return effectIndex < _gameObjectEffects.size();
    }

    @Override
    public Map<String, AwaitingDecision> processNextGameEffect(GameObjects gameObjects, DigitalObject context) {
        int indexToExecute = getEffectIndex(gameObjects, context);
        final GameObjectEffectSerie.Result result = _gameObjectEffects.get(indexToExecute).execute(gameObjects, context);

        if (!result._shouldContinue)
            setEffectIndex(gameObjects, context, indexToExecute+1);

        return result._decisions;
    }

    private int getEffectIndex(GameObjects gameObjects, DigitalObject context) {
        final String effectIndexStr = context.getAttributes().get(_indexAttribute.getValue(gameObjects, context));
        int effectIndex = 0;
        if (effectIndexStr != null)
            effectIndex = Integer.parseInt(effectIndexStr);
        return effectIndex;
    }

    private void setEffectIndex(GameObjects gameObjects, DigitalObject context, int value) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        digitalEnvironment.updateObject(context.getId(), Collections.singletonMap(_indexAttribute.getValue(gameObjects, context), String.valueOf(value)), false);
    }
}
