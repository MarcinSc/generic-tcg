package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

import java.util.List;

public class SubtractIntEvaluator implements IntEvaluator {
    private IntEvaluator _base;
    private List<IntEvaluator> _numbers;

    public void setBase(IntEvaluator base) {
        _base = base;
    }

    public void setNumbers(List<IntEvaluator> numbers) {
        _numbers = numbers;
    }

    @Override
    public int getIntValue(GameObjects gameObjects, GameEffectContext context) {
        int result = _base.getIntValue(gameObjects, context);
        for (IntEvaluator number : _numbers) {
            result -= number.getIntValue(gameObjects, context);
        }

        return result;
    }

    @Override
    public String getValue(GameObjects gameObjects, GameEffectContext context) {
        return String.valueOf(getIntValue(gameObjects, context));
    }
}
