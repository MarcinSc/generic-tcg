package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface StringEvaluator {
    public String getValue(GameState gameState, GameObject gameObject);
}
