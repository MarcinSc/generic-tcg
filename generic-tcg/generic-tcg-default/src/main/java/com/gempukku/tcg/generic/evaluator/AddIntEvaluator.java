package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;

import java.util.List;

public class AddIntEvaluator implements IntEvaluator {
    private List<IntEvaluator> _numbers;

    public void setNumbers(List<IntEvaluator> numbers) {
        _numbers = numbers;
    }

    @Override
    public int getIntValue(GameObjects gameObjects, GameEffectContext context) {
        int result = 0;
        for (IntEvaluator number : _numbers) {
            result += number.getIntValue(gameObjects, context);
        }

        return result;
    }

    @Override
    public String getValue(GameObjects gameObjects, GameEffectContext context) {
        return String.valueOf(getIntValue(gameObjects, context));
    }
}
