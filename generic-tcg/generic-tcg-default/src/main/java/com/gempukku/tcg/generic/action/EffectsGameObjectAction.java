package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.effect.GameObjectEffectSerie;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EffectsGameObjectAction implements GameAction {
    private List<? extends GameObjectEffectSerie> _gameObjectEffects;

    public void setEffects(List<? extends GameObjectEffectSerie> gameObjectEffects) {
        _gameObjectEffects = gameObjectEffects;
    }

    @Override
    public boolean hasNextGameEffect(GameObjects gameObjects, DigitalObject context) {
        int effectIndex = getEffectIndex(context);

        return effectIndex < _gameObjectEffects.size();
    }

    @Override
    public Map<String, AwaitingDecision> processNextGameEffect(GameObjects gameObjects, DigitalObject context) {
        int indexToExecute = getEffectIndex(context);
        final GameObjectEffectSerie.Result result = _gameObjectEffects.get(indexToExecute).execute(gameObjects, context);

        if (!result._shouldContinue)
            setEffectIndex(gameObjects, context, indexToExecute+1);

        return result._decisions;
    }

    private int getEffectIndex(DigitalObject object) {
        final String effectIndexStr = object.getAttributes().get("effectIndex");
        int effectIndex = 0;
        if (effectIndexStr != null)
            effectIndex = Integer.parseInt(effectIndexStr);
        return effectIndex;
    }

    private void setEffectIndex(GameObjects gameObjects, DigitalObject object, int value) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        digitalEnvironment.updateObject(object.getId(), Collections.singletonMap("effectIndex", String.valueOf(value)), false);
    }
}
