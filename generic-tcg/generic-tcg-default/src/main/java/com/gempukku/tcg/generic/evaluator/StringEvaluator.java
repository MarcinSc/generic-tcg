package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public interface StringEvaluator {
    public String getValue(GameObjects gameObjects, DigitalObject context);
}
