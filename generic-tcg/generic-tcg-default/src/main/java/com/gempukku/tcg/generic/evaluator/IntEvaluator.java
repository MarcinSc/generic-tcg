package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface IntEvaluator {
    public int getValue(GameState gameState, GameObject gameObject);
}
