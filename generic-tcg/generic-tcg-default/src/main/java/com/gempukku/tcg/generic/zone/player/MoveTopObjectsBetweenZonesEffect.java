package com.gempukku.tcg.generic.zone.player;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class MoveTopObjectsBetweenZonesEffect implements GameEffect {
    private IntEvaluator _count;
    private StringEvaluator _zoneFrom;
    private StringEvaluator _zoneTo;
    private StringEvaluator _playerFrom;
    private StringEvaluator _playerTo;

    public void setCount(IntEvaluator count) {
        _count = count;
    }

    public void setZoneFrom(StringEvaluator zoneFrom) {
        _zoneFrom = zoneFrom;
    }

    public void setZoneTo(StringEvaluator zoneTo) {
        _zoneTo = zoneTo;
    }

    public void setPlayerFrom(StringEvaluator playerFrom) {
        _playerFrom = playerFrom;
    }

    public void setPlayerTo(StringEvaluator playerTo) {
        _playerTo = playerTo;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final String zoneFromName = _zoneFrom.getValue(gameObjects, context);
        final String zoneToName = _zoneTo.getValue(gameObjects, context);
        final String playerFrom = _playerFrom.getValue(gameObjects, context);
        final String playerTo = _playerTo.getValue(gameObjects, context);
        final int count = _count.getIntValue(gameObjects, context);

        final DigitalObjectZoneManager zoneFrom = (DigitalObjectZoneManager) gameObjects.getGameObject(zoneFromName);
        final DigitalObjectZoneManager zoneTo = (DigitalObjectZoneManager) gameObjects.getGameObject(zoneToName);

        for (int i=0; i<count; i++) {
            final DigitalObject digitalObject = zoneFrom.removeTopObject(gameObjects, playerFrom);
            if (digitalObject != null)
                zoneTo.putOnTop(gameObjects, playerTo, digitalObject);
        }

        return Result.pass();
    }
}
