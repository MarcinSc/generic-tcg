package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.*;

public abstract class ChooseGameObjectDecision implements AwaitingDecision {
    private String _message;
    private List<GameObject> _gameObjects;

    public ChooseGameObjectDecision(String message, Collection<GameObject> gameObjects) {
        _message = message;
        _gameObjects = new LinkedList<GameObject>(gameObjects);
    }

    @Override
    public Map<String, String> getParameters(GameState gameState) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("message", _message);
        StringBuilder sb = new StringBuilder();
        boolean afterFirst = false;
        for (GameObject gameObject : _gameObjects) {
            if (!afterFirst)
                sb.append(",");
            sb.append(gameObject.getIdentifier());
            afterFirst = true;
        }
        params.put("ids", sb.toString());

        return params;
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        for (GameObject gameObject : _gameObjects) {
            if (gameObject.getIdentifier().equals(answer)) {
                gameObjectChosen(gameObject);
                return;
            }
        }
        throw new InvalidAnswerException("Game object with id: " + answer + " not found");
    }

    protected abstract void gameObjectChosen(GameObject gameObject);

    @Override
    public String getType() {
        return "CHOOSE_GAME_OBJECT";
    }
}
