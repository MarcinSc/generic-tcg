package com.gempukku.tcg.generic.zone;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class ShuffleZoneEffect implements GameEffect {
    private StringEvaluator _zone;
    private StringEvaluator _player;

    public void setZone(StringEvaluator zone) {
        _zone = zone;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final String zoneName = _zone.getValue(gameObjects, context);
        final String player = _player.getValue(gameObjects, context);

        final PlayerDigitalObjectZoneManager zone = (PlayerDigitalObjectZoneManager) gameObjects.getGameObject(zoneName);
        zone.shuffleItemsInZone(gameObjects, player);
        return Result.pass();
    }
}
