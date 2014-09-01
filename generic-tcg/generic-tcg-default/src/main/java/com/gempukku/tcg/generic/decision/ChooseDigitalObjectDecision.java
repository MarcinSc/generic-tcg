package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

import java.util.*;

public abstract class ChooseDigitalObjectDecision implements AwaitingDecision {
    private String _message;
    private List<DigitalObject> _objects;

    public ChooseDigitalObjectDecision(String message, Collection<DigitalObject> objects) {
        _message = message;
        _objects = new LinkedList<DigitalObject>(objects);
    }

    @Override
    public Map<String, String> getParameters(GameObjects gameState) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("message", _message);
        StringBuilder sb = new StringBuilder();
        boolean afterFirst = false;
        for (DigitalObject object : _objects) {
            if (!afterFirst)
                sb.append(",");
            sb.append(getIdentifier(object));
            afterFirst = true;
        }
        params.put("ids", sb.toString());

        return params;
    }

    private String getIdentifier(DigitalObject object) {
        return object.getId();
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        for (DigitalObject object : _objects) {
            if ((getIdentifier(object)).equals(answer)) {
                objectChosen(object);
                return;
            }
        }
        throw new InvalidAnswerException("Game object with id: " + answer + " not found");
    }

    protected abstract void objectChosen(DigitalObject object);

    @Override
    public String getType() {
        return "CHOOSE_OBJECT";
    }
}