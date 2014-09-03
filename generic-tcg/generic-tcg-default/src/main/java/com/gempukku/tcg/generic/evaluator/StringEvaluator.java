package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

public interface StringEvaluator {
    public String getValue(GameObjects gameObjects, GameActionContext context);
}
