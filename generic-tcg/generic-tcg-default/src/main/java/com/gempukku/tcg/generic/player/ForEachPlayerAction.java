package com.gempukku.tcg.generic.player;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ForEachPlayerAction implements GameAction {
    private GameAction _gameAction;
    private StringEvaluator _playerAttribute;

    public void setGameAction(GameAction gameAction) {
        _gameAction = gameAction;
    }

    public void setPlayerAttribute(StringEvaluator playerAttribute) {
        _playerAttribute = playerAttribute;
    }

    @Override
    public boolean hasNextGameEffect(GameObjects gameObjects, DigitalObject context) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final PlayerManager playerManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_MANAGER);

        final String attributeName = _playerAttribute.getValue(gameObjects, context);
        for (String playerName : playerManager.getPlayerNames(gameObjects)) {
            digitalEnvironment.updateObject(context.getId(), Collections.singletonMap(attributeName, playerName), false);
            if (_gameAction.hasNextGameEffect(gameObjects, context))
                return true;
        }

        return false;
    }

    @Override
    public Map<String, AwaitingDecision> processNextGameEffect(GameObjects gameObjects, DigitalObject context) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final PlayerManager playerManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_MANAGER);

        Map<String, AwaitingDecision> decisions = new HashMap<String, AwaitingDecision>();

        final String attributeName = _playerAttribute.getValue(gameObjects, context);
        for (String playerName : playerManager.getPlayerNames(gameObjects)) {
            digitalEnvironment.updateObject(context.getId(), Collections.singletonMap(attributeName, playerName), false);
            if (_gameAction.hasNextGameEffect(gameObjects, context)) {
                final Map<String, AwaitingDecision> playerResult = _gameAction.processNextGameEffect(gameObjects, context);
                if (playerResult != null)
                    decisions.putAll(playerResult);
            }
        }

        if (decisions.isEmpty())
            return null;
        return decisions;
    }
}
