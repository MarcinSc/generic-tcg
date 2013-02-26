package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.*;

public abstract class ChooseGameObjectDecision implements AwaitingDecision {
    private String _message;
    private List<GameObject> _objects;

    public ChooseGameObjectDecision(String message, Collection<GameObject> objects) {
        _message = message;
        _objects = new LinkedList<GameObject>(objects);
    }

    @Override
    public Map<String, String> getParameters(GameState gameState) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("message", _message);
        StringBuilder sb = new StringBuilder();
        boolean afterFirst = false;
        for (GameObject object : _objects) {
            if (!afterFirst)
                sb.append(",");
            sb.append(getIdentifier(object));
            afterFirst = true;
        }
        params.put("ids", sb.toString());

        return params;
    }

    private String getIdentifier(GameObject object) {
        return "object:" + object.getIdentifier();
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        for (GameObject object : _objects) {
            if ((getIdentifier(object)).equals(answer)) {
                objectChosen(object);
                return;
            }
        }
        throw new InvalidAnswerException("Game object with id: " + answer + " not found");
    }

    protected abstract void objectChosen(GameObject object);

    @Override
    public String getType() {
        return "CHOOSE_OBJECT";
    }
}