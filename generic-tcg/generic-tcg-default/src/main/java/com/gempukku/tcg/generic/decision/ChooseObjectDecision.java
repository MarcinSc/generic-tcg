package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameState;

import java.util.*;

public abstract class ChooseObjectDecision implements AwaitingDecision {
    private String _message;
    private List<String> _objects;

    public ChooseObjectDecision(String message, Collection<String> objects) {
        _message = message;
        _objects = new LinkedList<String>(objects);
    }

    @Override
    public Map<String, String> getParameters(GameState gameState) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("message", _message);
        StringBuilder sb = new StringBuilder();
        boolean afterFirst = false;
        for (String object : _objects) {
            if (afterFirst)
                sb.append(",");
            sb.append(object);
            afterFirst = true;
        }
        params.put("ids", sb.toString());

        return params;
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        for (String object : _objects) {
            if (object.equals(answer)) {
                objectChosen(object);
                return;
            }
        }
        throw new InvalidAnswerException("Game object with id: " + answer + " not found");
    }

    protected abstract void objectChosen(String object);

    @Override
    public String getType() {
        return "CHOOSE_OBJECT";
    }
}
