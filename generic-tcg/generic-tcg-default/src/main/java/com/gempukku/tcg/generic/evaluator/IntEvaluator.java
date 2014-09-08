package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

public interface IntEvaluator extends StringEvaluator {
    public int getIntValue(GameObjects gameObjects, GameActionContext context);
}
