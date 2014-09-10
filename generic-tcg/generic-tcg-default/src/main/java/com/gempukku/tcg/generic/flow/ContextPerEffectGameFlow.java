package com.gempukku.tcg.generic.flow;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GameFlow;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextPerEffectGameFlow implements GameFlow {
    private List<GameEffect> _effects;

    public void setEffects(List<GameEffect> effects) {
        _effects = effects;
    }

    @Override
    public Map<String, AwaitingDecision> processGameState(GameObjects gameObjects) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);

        String subActionIndex = DigitalObjects.getSimpleFlag(gameObjects, "subActionIndex");
        int index = 0;
        if (subActionIndex != null) {
            index = Integer.parseInt(subActionIndex);
            DigitalObjects.setSimpleFlag(gameObjects, "subActionIndex", String.valueOf(index));
        }

        DigitalObject actionObject = getActionObject(gameObjects, digitalEnvironment);
        final GameEffect gameAction = _effects.get(index);
        GameEffectContext context = new ObjectGameEffectContext(gameObjects, actionObject);
        final GameEffect.Result result = gameAction.execute(gameObjects, context);
        if (!result._shouldContinue) {
            digitalEnvironment.destroyObject(actionObject.getId());
            if (index + 1 < _effects.size()) {
                DigitalObjects.setSimpleFlag(gameObjects, "subActionIndex", String.valueOf(index + 1));
            } else {
                DigitalObjects.removeSimpleFlag(gameObjects, "subActionIndex");
            }
        }
        return result._decisions;
    }

    private DigitalObject getActionObject(GameObjects gameObjects, DigitalEnvironment digitalEnvironment) {
        DigitalObject actionObject = DigitalObjects.extractObject(gameObjects, "multiActionObject");
        if (actionObject == null) {
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("type", "multiActionObject");
            actionObject = digitalEnvironment.createObject(attrs);
        }
        return actionObject;
    }
}
