package com.gempukku.tcg.generic.events;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.EventAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public abstract class ChooseRandomEvent implements Event {
    private List<String> _values;

    public ChooseRandomEvent(Collection<String> values) {
        _values = new ArrayList<String>(values);
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        Random rnd = new Random();
        String randomValue = _values.get(rnd.nextInt(_values.size()));
        valueChosen(randomValue, action, actionStack, gameState, decisionCallback);
    }

    protected abstract void valueChosen(String value, EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback);
}
