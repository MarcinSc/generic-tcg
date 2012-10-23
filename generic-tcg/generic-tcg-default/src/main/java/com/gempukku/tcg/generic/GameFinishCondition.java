package com.gempukku.tcg.generic;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;

public interface GameFinishCondition {
    public boolean isFinished(GameState gameState, DecisionCallback decisionCallback);
}
