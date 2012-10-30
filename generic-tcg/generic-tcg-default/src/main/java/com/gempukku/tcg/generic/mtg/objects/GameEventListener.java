package com.gempukku.tcg.generic.mtg.objects;

import com.gempukku.tcg.GameState;

import java.util.Map;

public interface GameEventListener {
    public void triggerHappened(GameState gameState, TriggerContainer triggerContainer, String type, Map<String, String> params);
}
