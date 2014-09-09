package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class MoveTopObjectsBetweenStacksEffect implements GameEffect {
    private IntEvaluator _count;
    private StringEvaluator _player;
    private StringEvaluator _stackFrom;
    private StringEvaluator _stackTo;

    public void setCount(IntEvaluator count) {
        _count = count;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    public void setStackFrom(StringEvaluator stackFrom) {
        _stackFrom = stackFrom;
    }

    public void setStackTo(StringEvaluator stackTo) {
        _stackTo = stackTo;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        final String stackFromName = _stackFrom.getValue(gameObjects, context);
        final String stackToName = _stackTo.getValue(gameObjects, context);
        final String player = _player.getValue(gameObjects, context);
        final int count = _count.getIntValue(gameObjects, context);

        final PlayerDigitalObjectStackManager stackFrom = (PlayerDigitalObjectStackManager) gameObjects.getGameObject(stackFromName);
        final PlayerDigitalObjectStackManager stackTo = (PlayerDigitalObjectStackManager) gameObjects.getGameObject(stackToName);

        for (int i=0; i<count; i++) {
            final DigitalObject digitalObject = stackFrom.removeTopObject(gameObjects, player);
            if (digitalObject != null)
                stackTo.putOnTop(gameObjects, player, digitalObject);
        }

        return Result.pass();
    }
}
