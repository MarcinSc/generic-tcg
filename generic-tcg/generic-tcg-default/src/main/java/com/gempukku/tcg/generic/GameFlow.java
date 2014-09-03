package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.Map;

public interface GameFlow {
    public Map<String, AwaitingDecision> processGameState(GameObjects gameObjects);
}
