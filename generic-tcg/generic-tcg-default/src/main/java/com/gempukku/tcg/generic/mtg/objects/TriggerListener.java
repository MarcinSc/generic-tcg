package com.gempukku.tcg.generic.mtg.objects;

import java.util.Map;

public interface TriggerListener {
    public void triggerHappened(TriggerContainer triggerContainer, String type, Map<String, String> params);
}
