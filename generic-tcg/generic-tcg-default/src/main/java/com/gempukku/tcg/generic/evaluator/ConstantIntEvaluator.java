package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class ConstantIntEvaluator implements IntEvaluator {
    private int _value;

    public void setValue(int value) {
        _value = value;
    }

    @Override
    public int getValue(GameState gameState, GameObject gameObject) {
        return _value;
    }
}
