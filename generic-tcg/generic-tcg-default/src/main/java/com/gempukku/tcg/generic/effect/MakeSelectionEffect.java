package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.decision.ChooseObjectDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.GameObjectFilter;
import com.gempukku.tcg.generic.filter.PlayerFilter;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.other.Property;
import com.gempukku.tcg.generic.util.GameObjectUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MakeSelectionEffect extends GameObjectEffect {
    private String _decisionHolder;
    private String _gameObjectManager;
    private String _playersProperty;

    private String _selectingPlayer;
    private String _destinationProperty;
    private String _zone;
    private String _playerZone;
    private GameObjectFilter _gameObjectFilter;
    private PlayerFilter _playerFilter;
    private StringEvaluator _message;

    public void setDecisionHolder(String decisionHolder) {
        _decisionHolder = decisionHolder;
    }

    public void setGameObjectManager(String gameObjectManager) {
        _gameObjectManager = gameObjectManager;
    }

    public void setPlayersProperty(String playersProperty) {
        _playersProperty = playersProperty;
    }

    public void setSelectingPlayer(String selectingPlayer) {
        _selectingPlayer = selectingPlayer;
    }

    public void setDestinationProperty(String destinationProperty) {
        _destinationProperty = destinationProperty;
    }

    public void setZone(String zone) {
        _zone = zone;
    }

    public void setPlayerZone(String playerZone) {
        _playerZone = playerZone;
    }

    public void setGameObjectFilter(GameObjectFilter gameObjectFilter) {
        _gameObjectFilter = gameObjectFilter;
    }

    public void setPlayerFilter(PlayerFilter playerFilter) {
        _playerFilter = playerFilter;
    }

    public void setMessage(StringEvaluator message) {
        _message = message;
    }

    @Override
    public void executeGameEffect(GameState gameState, final GameObject gameObject) {
        List<String> possibleSelections = getPossibleSelections(gameState, gameObject);

        DecisionHolder decisionHolder = (DecisionHolder) gameState.getPlayerObject(GameObjectUtils.resolveObjectProperty(gameObject, _selectingPlayer), _decisionHolder);
        decisionHolder.setDecision(
                new ChooseObjectDecision(_message.getValue(gameState, gameObject), possibleSelections) {
                    @Override
                    protected void objectChosen(String object) {
                        gameObject.setProperty(_destinationProperty, object);
                    }
                });
    }

    protected List<String> getPossibleSelections(final GameState gameState, GameObject gameObject) {
        final List<String> result = new LinkedList<String>();
        if (_gameObjectFilter != null)
            appendMatchingGameObjects(gameState, gameObject, result);
        if (_playerFilter != null)
            appendMatchingPlayers(gameState, gameObject, result);

        return result;
    }

    private void appendMatchingPlayers(GameState gameState, GameObject gameObject, List<String> result) {
        String[] players = ((Property) gameState.getGameObject(_playersProperty)).getValue().split(",");
        for (String player : players) {
            if (_playerFilter.matches(gameState, player))
                result.add("player:" +player);
        }
    }

    private void appendMatchingGameObjects(final GameState gameState, GameObject gameObject, final List<String> result) {
        GameObjectManager gameObjectManager = (GameObjectManager) gameState.getGameObject(_gameObjectManager);
        GameObjectVisitor visitor = new GameObjectVisitor() {
            @Override
            public boolean visitGameObject(Zone zone, GameObject visitedGameObject) {
                if (_gameObjectFilter.matches(gameState, visitedGameObject))
                    result.add("object:" + visitedGameObject.getIdentifier());

                return false;
            }
        };
        if (_zone != null) {
            final Set<GameObject> objectsMatching = gameObjectManager.findObjectsMatching(GameObjectUtils.resolveZone(gameState, gameObject, _zone, _playerZone), gameState, _gameObjectFilter);
            for (GameObject object : objectsMatching)
                result.add("object:"+object.getIdentifier());
        } else
            gameObjectManager.visitGameObjects(visitor);
    }
}
