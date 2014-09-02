package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public class AttributeValueStringEvaluator implements StringEvaluator {
    private String _propertyName;

    public void setPropertyName(String propertyName) {
        _propertyName = propertyName;
    }

    @Override
    public String getValue(GameObjects gameObjects, DigitalObject context) {
        return context.getAttributes().get(_propertyName);
    }
}
