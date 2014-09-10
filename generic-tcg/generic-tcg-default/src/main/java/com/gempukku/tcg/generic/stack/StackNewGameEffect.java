package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.zone.player.DigitalObjectZoneManager;

import java.util.HashMap;
import java.util.Map;

public class StackNewGameEffect implements GameEffect {
    private StringEvaluator _effectId;

    public void setEffectId(StringEvaluator effectId) {
        _effectId = effectId;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        DigitalObjectZoneManager stackZone = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.STACK_ZONE);

        String effectId = _effectId.getValue(gameObjects, context);

        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("type", "stackAction");
        attrs.put("effectId", effectId);
        
        DigitalObject actionObject = digitalEnvironment.createObject(attrs);
        stackZone.putOnTop(gameObjects, null, actionObject);

        return Result.pass();
    }
}
