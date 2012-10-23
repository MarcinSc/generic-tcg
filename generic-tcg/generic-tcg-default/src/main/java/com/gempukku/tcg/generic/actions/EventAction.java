package com.gempukku.tcg.generic.actions;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;

import java.util.LinkedList;
import java.util.List;

public class EventAction implements Action {
    private List<Event> _events = new LinkedList<Event>();
    private Action _nextAction;

    public void setEvents(List<Event> events) {
        _events.addAll(events);
    }

    public void addEvent(Event event) {
        _events.add(event);
    }

    public void insertEvent(Event event) {
        _events.add(0, event);
    }

    public void setNextAction(Action nextAction) {
        _nextAction = nextAction;
    }

    @Override
    public void processNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        Event event = _events.remove(0);
        event.processEvent(this, actionStack, gameState, decisionCallback);
    }

    @Override
    public boolean hasNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        return !_events.isEmpty();
    }

    @Override
    public Action getNextAction(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        return _nextAction;
    }
}
