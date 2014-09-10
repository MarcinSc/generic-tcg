package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.util.StringUtils;

import java.util.Collections;

public class SetObjectsAttributeEffect implements GameEffect {
    private StringEvaluator _ids;
    private StringEvaluator _attributeName;
    private StringEvaluator _attributeValue;

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    public void setAttributeValue(StringEvaluator attributeValue) {
        _attributeValue = attributeValue;
    }

    public void setIds(StringEvaluator ids) {
        _ids = ids;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);

        final String[] ids = StringUtils.correctSplit(_ids.getValue(gameObjects, context), ",");
        final String name = _attributeName.getValue(gameObjects, context);
        final String value = _attributeValue.getValue(gameObjects, context);

        for (String id : ids)
            digitalEnvironment.updateObject(id, Collections.singletonMap(name, value), false);

        return Result.pass();
    }
}
