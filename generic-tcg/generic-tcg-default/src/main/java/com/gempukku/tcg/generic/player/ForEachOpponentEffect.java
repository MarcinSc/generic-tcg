package com.gempukku.tcg.generic.player;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ForEachOpponentEffect implements GameEffect {
    private StringEvaluator _indexAttribute = new ConstantStringEvaluator("remainingPlayers");
    private StringEvaluator _player;
    private GameEffect _gameEffect;
    private StringEvaluator _attributeName;

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    public void setGameEffect(GameEffect gameEffect) {
        _gameEffect = gameEffect;
    }

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    public void setIndexAttribute(StringEvaluator indexAttribute) {
        _indexAttribute = indexAttribute;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        String indexAttribute = _indexAttribute.getValue(gameObjects, context);
        String attributeName = _attributeName.getValue(gameObjects, context);

        List<String> opponents = getOpponents(gameObjects, context, indexAttribute);
        while (opponents.size() > 0) {
            String opponent = opponents.get(0);
            context.setAttribute(attributeName, opponent);
            Result result = _gameEffect.execute(gameObjects, context);
            if (!result._shouldContinue)
                opponents.remove(0);
            if (result._decisions != null) {
                context.setAttribute(indexAttribute, StringUtils.join(opponents, ","));
                return result;
            }
        }

        context.removeAttribute(indexAttribute);
        return Result.pass();
    }

    private List<String> getOpponents(GameObjects gameObjects, GameActionContext context, String indexAttribute) {
        String attribute = context.getAttribute(indexAttribute);
        if (attribute != null)
            return new LinkedList<String>(Arrays.asList(StringUtils.correctSplit(attribute, ",")));

        String player = _player.getValue(gameObjects, context);
        PlayerManager playerManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_MANAGER);
        return playerManager.getOpponents(gameObjects, player);
    }
}
