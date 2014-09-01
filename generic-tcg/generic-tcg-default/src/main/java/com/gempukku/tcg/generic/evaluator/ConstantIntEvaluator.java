package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public class ConstantIntEvaluator implements IntEvaluator {
    private int _value;

    public void setValue(int value) {
        _value = value;
    }

    @Override
    public int getValue(GameObjects gameState, DigitalObject digitalObject) {
        return _value;
    }
}
