package com.gempukku.tcg.generic.effect.flag;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

public class SetFlagEffect implements GameEffect {
    private StringEvaluator _flagName;
    private StringEvaluator _flagValue;

    public void setFlagName(StringEvaluator flagName) {
        _flagName = flagName;
    }

    public void setFlagValue(StringEvaluator flagValue) {
        _flagValue = flagValue;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        String name = _flagName.getValue(gameObjects, context);
        String value = _flagValue.getValue(gameObjects, context);

        DigitalObjects.setSimpleFlag(gameObjects, name, value);
        
        return new Result(null, false);
    }
}
