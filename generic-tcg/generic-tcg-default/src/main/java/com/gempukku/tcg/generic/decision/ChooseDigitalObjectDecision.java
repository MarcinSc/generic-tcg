package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.digital.DigitalObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class ChooseDigitalObjectDecision implements AwaitingDecision {
    private String _message;
    private List<DigitalObject> _objects;
    private int _min;
    private int _max;

    public ChooseDigitalObjectDecision(String message, Collection<DigitalObject> objects, int min, int max) {
        _message = message;
        _objects = new LinkedList<DigitalObject>(objects);

        _min = Math.min(min, objects.size());
        _max = Math.min(max, objects.size());
    }

    public String getMessage() {
        return _message;
    }

    public List<DigitalObject> getObjects() {
        return _objects;
    }

    public int getMin() {
        return _min;
    }

    public int getMax() {
        return _max;
    }

    @Override
    public void processAnswer(String answer) throws InvalidAnswerException {
        final String[] split = answer.split(",");
        try {
            Set<DigitalObject> objects = new HashSet<DigitalObject>();

            for (String objectIds : split) {
                for (DigitalObject object : _objects) {
                    if (object.getId().equals(objectIds)) {
                        objects.add(object);
                        break;
                    }
                }
            }

            if (objects.size() < _min || objects.size() > _max)
                throw new InvalidAnswerException("Invalid number of objects selected");

            objectsChosen(objects);
        } catch (NumberFormatException exp) {
            throw new InvalidAnswerException("Invalid item index presented");
        }
    }

    protected abstract void objectsChosen(Set<DigitalObject> object);
}