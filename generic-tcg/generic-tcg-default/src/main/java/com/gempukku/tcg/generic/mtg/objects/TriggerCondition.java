package com.gempukku.tcg.generic.mtg.objects;

import com.gempukku.tcg.GameState;

import java.util.Map;

public interface TriggerCondition {
    public Trigger generateTrigger(GameState gameState, String type, Map<String, String> params);
}
