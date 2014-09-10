package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class ShuffleStackEffect implements GameEffect {
    private StringEvaluator _stack;
    private StringEvaluator _player;

    public void setStack(StringEvaluator stack) {
        _stack = stack;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final String stackName = _stack.getValue(gameObjects, context);
        final String player = _player.getValue(gameObjects, context);

        final PlayerDigitalObjectStackManager stack = (PlayerDigitalObjectStackManager) gameObjects.getGameObject(stackName);
        stack.shuffleItemsInStack(gameObjects, player);
        return Result.pass();
    }
}
