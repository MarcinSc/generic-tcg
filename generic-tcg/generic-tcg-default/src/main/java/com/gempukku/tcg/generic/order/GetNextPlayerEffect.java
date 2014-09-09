package com.gempukku.tcg.generic.order;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class GetNextPlayerEffect implements GameEffect {
    private StringEvaluator _previous;
    private StringEvaluator _attribute;

    public void setAttribute(StringEvaluator attribute) {
        _attribute = attribute;
    }

    public void setPrevious(StringEvaluator previous) {
        _previous = previous;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        final PlayerOrder playerOrder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_ORDER);
        final String attribute = _attribute.getValue(gameObjects, context);
        final String previous = _previous.getValue(gameObjects, context);
        context.setAttribute(attribute, playerOrder.getNextPlayerAfter(gameObjects, previous));

        return Result.pass();
    }
}
