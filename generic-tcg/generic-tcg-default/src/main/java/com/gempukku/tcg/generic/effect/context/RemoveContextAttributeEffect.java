package com.gempukku.tcg.generic.effect.context;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.util.StringUtils;

public class RemoveContextAttributeEffect implements GameEffect {
    private StringEvaluator _attributeName;

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final String attributeName = _attributeName.getValue(gameObjects, context);
        for (String attribute : StringUtils.correctSplit(attributeName, ","))
            context.removeAttribute(attribute);
        
        return Result.pass();
    }
}
