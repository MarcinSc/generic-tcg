package com.gempukku.tcg.generic.mtg.objects;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TriggerManager {
    private TriggerContainer _triggerContainer = new TriggerContainer();

    private List<TriggerListener> _triggerListeners = new LinkedList<TriggerListener>();

    public void setTriggerListeners(Set<TriggerListener> triggerListeners) {
        _triggerListeners.addAll(triggerListeners);
    }

    public void addTriggerListener(TriggerListener triggerListener) {
        _triggerListeners.add(triggerListener);
    }

    public void removeTriggerListener(TriggerListener triggerListener) {
        _triggerListeners.remove(triggerListener);
    }

    public void distributeTrigger(String type, Map<String, String> params) {
        for (TriggerListener triggerListener : _triggerListeners)
            triggerListener.triggerHappened(_triggerContainer, type, params);
    }

    public Set<Trigger> consumeAwaitingTriggers() {
        return _triggerContainer.consumeWaitingTriggers();
    }
}
