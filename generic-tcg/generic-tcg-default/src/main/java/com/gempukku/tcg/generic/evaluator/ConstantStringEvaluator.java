package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

public class ConstantStringEvaluator implements StringEvaluator {
    private String _value;

    public ConstantStringEvaluator() {
    }

    public ConstantStringEvaluator(String value) {
        _value = value;
    }

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public String getValue(GameObjects gameObjects, GameActionContext context) {
        return _value;
    }
}
