package com.gempukku.tcg.generic.decision;

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
    public void accept(AwaitingDecisionVisitor visitor) {
        visitor.visit(this);
    }

    public String getMessage() {
        return _message;
    }

    public List<DigitalObject> getObjects() {
        return _objects;
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
}