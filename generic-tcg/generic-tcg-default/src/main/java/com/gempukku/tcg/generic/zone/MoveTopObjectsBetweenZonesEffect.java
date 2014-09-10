package com.gempukku.tcg.generic.zone;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class MoveTopObjectsBetweenZonesEffect implements GameEffect {
    private IntEvaluator _count;
    private StringEvaluator _player;
    private StringEvaluator _zoneFrom;
    private StringEvaluator _zoneTo;

    public void setCount(IntEvaluator count) {
        _count = count;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    public void setZoneFrom(StringEvaluator zoneFrom) {
        _zoneFrom = zoneFrom;
    }

    public void setZoneTo(StringEvaluator zoneTo) {
        _zoneTo = zoneTo;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final String zoneFromName = _zoneFrom.getValue(gameObjects, context);
        final String zoneToName = _zoneTo.getValue(gameObjects, context);
        final String player = _player.getValue(gameObjects, context);
        final int count = _count.getIntValue(gameObjects, context);

        final PlayerDigitalObjectZoneManager zoneFrom = (PlayerDigitalObjectZoneManager) gameObjects.getGameObject(zoneFromName);
        final PlayerDigitalObjectZoneManager zoneTo = (PlayerDigitalObjectZoneManager) gameObjects.getGameObject(zoneToName);

        for (int i=0; i<count; i++) {
            final DigitalObject digitalObject = zoneFrom.removeTopObject(gameObjects, player);
            if (digitalObject != null)
                zoneTo.putOnTop(gameObjects, player, digitalObject);
        }

        return Result.pass();
    }
}
