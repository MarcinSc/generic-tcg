package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class ConstantStringEvaluator implements StringEvaluator {
    private String _value;

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public String getValue(GameState gameState, GameObject gameObject) {
        return _value;
    }
}
