package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GameFlow;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.Map;

public class StartPhaseGameFlow implements GameFlow {
    @Override
    public Map<String, AwaitingDecision> processGameState(GameObjects gameObjects) {
        return null;
    }
}
