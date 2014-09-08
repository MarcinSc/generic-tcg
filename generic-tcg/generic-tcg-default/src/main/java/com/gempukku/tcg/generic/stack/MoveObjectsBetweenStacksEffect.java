package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.util.StringUtils;

import java.util.List;

public class MoveObjectsBetweenStacksEffect implements GameEffect {
    private StringEvaluator _stackFrom;
    private StringEvaluator _stackTo;
    private StringEvaluator _ids;
    private StringEvaluator _player;

    public void setIds(StringEvaluator ids) {
        _ids = ids;
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

        final PlayerDigitalObjectStackManager stackFrom = (PlayerDigitalObjectStackManager) gameObjects.getGameObject(stackFromName);
        final PlayerDigitalObjectStackManager stackTo = (PlayerDigitalObjectStackManager) gameObjects.getGameObject(stackToName);

        if (_ids != null) {
            final String[] ids = StringUtils.correctSplit(_ids.getValue(gameObjects, context), ",");

            for (String id : ids) {
                final DigitalObject object = stackFrom.removeObjectFromStack(gameObjects, player, id);
                stackTo.putOnTop(gameObjects, player, object);
            }
        } else {
            final List<DigitalObject> objects = stackFrom.getDigitalObjectsInStack(gameObjects, player);
            stackFrom.removeAllObjectsInStack(gameObjects, player);
            for (DigitalObject object : objects)
                stackTo.putOnTop(gameObjects, player, object);
        }

        return new Result(null, false);
    }
}
