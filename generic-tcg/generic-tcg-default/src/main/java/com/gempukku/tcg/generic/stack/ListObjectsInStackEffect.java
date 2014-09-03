package com.gempukku.tcg.generic.stack;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ListObjectsInStackEffect implements GameEffect {
    private StringEvaluator _stack;
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

    public void setStack(StringEvaluator stackType) {
        _stack = stackType;
    }

    @Override
    public Result execute(final GameObjects gameObjects, final GameActionContext context) {
        final PlayerDigitalObjectStackManager stack = (PlayerDigitalObjectStackManager) gameObjects.getGameObject(_stack.getValue(gameObjects, context));

        final String playerName = _player.getValue(gameObjects, context);

        final List<DigitalObject> matchingObjects = DigitalObjectUtils.filter(gameObjects, _digitalObjectFilter, context, stack.getDigitalObjectsInStack(gameObjects, playerName));

        String attributeName = _attributeName.getValue(gameObjects, context);
        context.setAttribute(attributeName, StringUtils.join(
                Iterators.transform(matchingObjects.iterator(),
                        new Function<DigitalObject, String>() {
                            @Override
                            public String apply(DigitalObject input) {
                                return input.getId();
                            }
                        }), ","));

        return new Result(null, false);
    }

}
