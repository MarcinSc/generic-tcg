package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffectResolver;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.flow.ObjectGameEffectContext;
import com.gempukku.tcg.generic.zone.player.DigitalObjectZoneManager;

public class StackResolveEffect implements GameEffect {
    private StringEvaluator _zone;
    private GameEffect _gameEffect;

    public void setZone(StringEvaluator zone) {
        _zone = zone;
    }

    public void setGameEffect(GameEffect gameEffect) {
        _gameEffect = gameEffect;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        GameEffectResolver gameEffectResolver = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.GAME_EFFECT_RESOLVER);

        String zoneName = _zone.getValue(gameObjects, context);
        DigitalObjectZoneManager stackZone = (DigitalObjectZoneManager) gameObjects.getGameObject(zoneName);

        DigitalObject topAction;
        while ((topAction = stackZone.getTopObject(gameObjects, null)) != null) {
            String effectId = topAction.getAttributes().get("effectId");
            GameEffect gameEffect = gameEffectResolver.resolveGameEffect(effectId);
            ObjectGameEffectContext effectContext = new ObjectGameEffectContext(gameObjects, topAction);
            while (true) {
                Result result = gameEffect.execute(gameObjects, effectContext);
                if (!result._shouldContinue) {
                    stackZone.removeObjectFromZone(gameObjects, null, topAction.getId());
                    digitalEnvironment.destroyObject(topAction.getId());
                    break;
                }
                if (result._decisions != null)
                    return result;
            }
        }

        return _gameEffect.execute(gameObjects, context);
    }
}
