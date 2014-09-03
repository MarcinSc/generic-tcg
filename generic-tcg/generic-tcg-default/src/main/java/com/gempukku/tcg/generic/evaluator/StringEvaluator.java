package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.GameObjects;

public interface StringEvaluator {
    public String getValue(GameObjects gameObjects, GameActionContext context);
}
