package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;

public class ConstantIntEvaluator implements IntEvaluator {
    private String _value;

    public ConstantIntEvaluator() {
    }

    public ConstantIntEvaluator(int value) {
        _value = String.valueOf(value);
    }

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public int getIntValue(GameObjects gameObjects, GameEffectContext context) {
        return Integer.parseInt(DigitalObjectUtils.resolveObjectProperty(context, _value));
    }

    @Override
    public String getValue(GameObjects gameObjects, GameEffectContext context) {
        return String.valueOf(getIntValue(gameObjects, context));
    }
}
