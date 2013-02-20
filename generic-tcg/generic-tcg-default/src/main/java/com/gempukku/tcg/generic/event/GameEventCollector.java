package com.gempukku.tcg.generic.event;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameEventCollector implements GameEventListener {
    private List<GameEvent> _gameEvents = new LinkedList<GameEvent>();
    private GameEventCondition _resetCondition;

    public void setResetCondition(GameEventCondition resetCondition) {
        _resetCondition = resetCondition;
    }

    @Override
    public void processGameEvent(GameEvent gameEvent) {
        _gameEvents.add(gameEvent);
        if (_resetCondition.matches(gameEvent))
            _gameEvents.clear();
    }

    public List<GameEvent> getGameEvents() {
        return Collections.unmodifiableList(_gameEvents);
    }
}
