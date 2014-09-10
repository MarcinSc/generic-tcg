package com.gempukku.tcg.generic.zone;

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
    private StringEvaluator _player;

    public void setIds(StringEvaluator ids) {
        _ids = ids;
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

        final PlayerDigitalObjectZoneManager zoneFrom = (PlayerDigitalObjectZoneManager) gameObjects.getGameObject(zoneFromName);
        final PlayerDigitalObjectZoneManager zoneTo = (PlayerDigitalObjectZoneManager) gameObjects.getGameObject(zoneToName);

        if (_ids != null) {
            final String[] ids = StringUtils.correctSplit(_ids.getValue(gameObjects, context), ",");

            for (String id : ids) {
                final DigitalObject object = zoneFrom.removeObjectFromZone(gameObjects, player, id);
                zoneTo.putOnTop(gameObjects, player, object);
            }
        } else {
            final List<DigitalObject> objects = zoneFrom.getDigitalObjectsInZone(gameObjects, player);
            zoneFrom.removeAllObjectsInZone(gameObjects, player);
            for (DigitalObject object : objects)
                zoneTo.putOnTop(gameObjects, player, object);
        }

        return Result.pass();
    }
}
