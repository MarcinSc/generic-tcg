package com.gempukku.tcg.generic.zone.player;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.util.StringUtils;

import java.util.List;

public class MoveObjectsBetweenZonesEffect implements GameEffect {
    private StringEvaluator _zoneFrom;
    private StringEvaluator _zoneTo;
    private StringEvaluator _ids;
    private StringEvaluator _playerFrom;
    private StringEvaluator _playerTo;

    public void setIds(StringEvaluator ids) {
        _ids = ids;
    }

    public void setPlayerFrom(StringEvaluator playerFrom) {
        _playerFrom = playerFrom;
    }

    public void setPlayerTo(StringEvaluator playerTo) {
        _playerTo = playerTo;
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
        final String playerFrom = _playerFrom.getValue(gameObjects, context);
        String playerTo = null;
        if (_playerTo != null)
            playerTo = _playerTo.getValue(gameObjects, context);

        final DigitalObjectZoneManager zoneFrom = (DigitalObjectZoneManager) gameObjects.getGameObject(zoneFromName);
        final DigitalObjectZoneManager zoneTo = (DigitalObjectZoneManager) gameObjects.getGameObject(zoneToName);

        if (_ids != null) {
            final String[] ids = StringUtils.correctSplit(_ids.getValue(gameObjects, context), ",");

            for (String id : ids) {
                final DigitalObject object = zoneFrom.removeObjectFromZone(gameObjects, playerFrom, id);
                zoneTo.putOnTop(gameObjects, playerTo, object);
            }
        } else {
            final List<DigitalObject> objects = zoneFrom.getDigitalObjectsInZone(gameObjects, playerFrom);
            zoneFrom.removeAllObjectsInZone(gameObjects, playerFrom);
            for (DigitalObject object : objects)
                zoneTo.putOnTop(gameObjects, playerTo, object);
        }

        return Result.pass();
    }
}
