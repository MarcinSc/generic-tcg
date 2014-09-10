package com.gempukku.tcg.generic.order;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class GetFirstPlayerEffect implements GameEffect {
    private StringEvaluator _attribute;

    public void setAttribute(StringEvaluator attribute) {
        _attribute = attribute;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final PlayerOrder playerOrder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_ORDER);
        final String attribute = _attribute.getValue(gameObjects, context);
        context.setAttribute(attribute, playerOrder.getFirstPlayer(gameObjects));

        return Result.pass();
    }
}
