package com.gempukku.tcg.generic.effect.decision;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.ChooseNumberDecision;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.HashMap;
import java.util.Map;

public class ChooseNumberEffect implements GameEffect {
    private StringEvaluator _message;
    private StringEvaluator _player;
    private IntEvaluator _min;
    private IntEvaluator _max;
    private StringEvaluator _attributeName;

    public void setMessage(StringEvaluator message) {
        _message = message;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    public void setMin(IntEvaluator min) {
        _min = min;
    }

    public void setMax(IntEvaluator max) {
        _max = max;
    }

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    @Override
    public Result execute(GameObjects gameObjects, final GameActionContext context) {
        final String attributeName = _attributeName.getValue(gameObjects, context);
        if (context.getAttribute(attributeName) != null)
            return Result.pass();

        Map<String, AwaitingDecision> decisions = new HashMap<String, AwaitingDecision>();

        String player = _player.getValue(gameObjects, context);
        String message = _message.getValue(gameObjects, context);
        int min = _min.getIntValue(gameObjects, context);
        int max = _max.getIntValue(gameObjects, context);
        decisions.put(player,
                new ChooseNumberDecision(message, min, max) {
                    @Override
                    protected void numberChosen(int value) {
                        context.setAttribute(attributeName, String.valueOf(value));
                    }
                });

        return Result.decisions(decisions);
    }
}
