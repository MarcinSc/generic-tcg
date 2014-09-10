package com.gempukku.tcg.generic.zone;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ListObjectsInZoneEffect implements GameEffect {
    private StringEvaluator _zone;
    private DigitalObjectFilter _digitalObjectFilter;
    private StringEvaluator _attributeName;
    private StringEvaluator _player;

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    public void setDigitalObjectFilter(DigitalObjectFilter digitalObjectFilter) {
        _digitalObjectFilter = digitalObjectFilter;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    public void setZone(StringEvaluator zone) {
        _zone = zone;
    }

    @Override
    public Result execute(final GameObjects gameObjects, final GameEffectContext context) {
        final PlayerDigitalObjectZoneManager zone = (PlayerDigitalObjectZoneManager) gameObjects.getGameObject(_zone.getValue(gameObjects, context));

        final String playerName = _player.getValue(gameObjects, context);

        final List<DigitalObject> matchingObjects = DigitalObjectUtils.filter(gameObjects, _digitalObjectFilter, context, zone.getDigitalObjectsInZone(gameObjects, playerName));

        String attributeName = _attributeName.getValue(gameObjects, context);
        context.setAttribute(attributeName, StringUtils.join(
                Iterators.transform(matchingObjects.iterator(),
                        new Function<DigitalObject, String>() {
                            @Override
                            public String apply(DigitalObject input) {
                                return input.getId();
                            }
                        }), ","));

        return Result.pass();
    }

}
