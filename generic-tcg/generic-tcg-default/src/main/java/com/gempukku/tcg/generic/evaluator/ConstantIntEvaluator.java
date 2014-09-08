package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

public class ConstantIntEvaluator implements IntEvaluator {
    private int _value;

    public ConstantIntEvaluator() {
    }

    public ConstantIntEvaluator(int value) {
        _value = value;
    }

    public void setValue(int value) {
        _value = value;
    }

    @Override
    public int getValue(GameObjects gameObjects, GameActionContext context) {
        return _value;
    }
}
