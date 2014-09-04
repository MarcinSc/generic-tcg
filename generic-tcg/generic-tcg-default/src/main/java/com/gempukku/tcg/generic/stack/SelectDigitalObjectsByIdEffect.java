package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.ChooseArbitraryCardDecision;
import com.gempukku.tcg.generic.decision.ChooseDigitalObjectDecision;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectDigitalObjectsByIdEffect implements GameEffect {
    private StringEvaluator _ids;
    private StringEvaluator _player;
    private StringEvaluator _attributeName;
    private StringEvaluator _message;
    private IntEvaluator _min;
    private IntEvaluator _max;

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    public void setIds(StringEvaluator ids) {
        _ids = ids;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    public void setMessage(StringEvaluator message) {
        _message = message;
    }

    public void setMax(IntEvaluator max) {
        _max = max;
    }

    public void setMin(IntEvaluator min) {
        _min = min;
    }

    @Override
    public Result execute(GameObjects gameObjects, final GameActionContext context) {
        final String attributeName = _attributeName.getValue(gameObjects, context);
        if (context.getAttribute(attributeName) != null)
            return new Result(null, false);

        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);

        final String playerName = _player.getValue(gameObjects, context);
        final String message = _message.getValue(gameObjects, context);
        final int min = _min.getValue(gameObjects, context);
        final int max = _max.getValue(gameObjects, context);

        Set<DigitalObject> digitalObjects = new HashSet<DigitalObject>();
        final String[] ids = _ids.getValue(gameObjects, context).split(",");
        for (String id : ids)
            digitalObjects.add(digitalEnvironment.getObjectById(id));

        Map<String, AwaitingDecision> decisions = new HashMap<String, AwaitingDecision>();
        decisions.put(playerName,
                new ChooseDigitalObjectDecision(message, digitalObjects, min, max) {
                    @Override
                    protected void objectsChosen(Set<DigitalObject> object) {
                        if (object.size() > 0) {
                            context.setAttribute(attributeName, StringUtils.join(
                                    Iterables.transform(object,
                                            new Function<DigitalObject, String>() {
                                                @Override
                                                public String apply(DigitalObject input) {
                                                    return input.getId();
                                                }

                                            }).iterator(), ","));
                        }
                    }
                });

        return new Result(decisions, true);
    }
}
