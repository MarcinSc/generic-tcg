package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public interface StringEvaluator {
    public String getValue(GameState gameState, DigitalObject digitalObject);
}
