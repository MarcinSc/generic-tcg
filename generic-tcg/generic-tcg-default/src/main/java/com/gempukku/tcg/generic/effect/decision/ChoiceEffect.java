package com.gempukku.tcg.generic.effect.decision;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.YesNoDecision;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.HashMap;
import java.util.Map;

public class ChoiceEffect implements GameEffect {
    private StringEvaluator _message;
    private StringEvaluator _player;
    private StringEvaluator _attributeName;

    public void setMessage(StringEvaluator message) {
        _message = message;
    }

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    @Override
    public Result execute(GameObjects gameObjects, final GameActionContext context) {
        final String attributeName = _attributeName.getValue(gameObjects, context);
        if (context.getAttribute(attributeName) != null)
            return Result.pass();

        String player = _player.getValue(gameObjects, context);
        String message = _message.getValue(gameObjects, context);

        Map<String, AwaitingDecision> decisions = new HashMap<String, AwaitingDecision>();
        decisions.put(player,
                new YesNoDecision(message) {
                    @Override
                    protected void yes() {
                        context.setAttribute(attributeName, "yes");
                    }

                    @Override
                    protected void no() {
                        context.setAttribute(attributeName, "no");
                    }
                });

        return Result.decisions(decisions);
    }
}
