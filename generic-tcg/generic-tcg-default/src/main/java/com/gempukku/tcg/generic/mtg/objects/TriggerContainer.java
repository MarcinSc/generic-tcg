package com.gempukku.tcg.generic.mtg.objects;

import java.util.HashSet;
import java.util.Set;

public class TriggerContainer {
    private Set<Trigger> _waitingTriggers = new HashSet<Trigger>();

    public void addWaitingTrigger(Trigger trigger) {
        _waitingTriggers.add(trigger);
    }

    public Set<Trigger> consumeWaitingTriggers() {
        Set<Trigger> result = _waitingTriggers;
        _waitingTriggers = new HashSet<Trigger>();
        return result;
    }
}
