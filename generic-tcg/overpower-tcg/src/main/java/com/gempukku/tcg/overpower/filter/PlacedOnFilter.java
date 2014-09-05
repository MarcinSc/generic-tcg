package com.gempukku.tcg.overpower.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;

public class PlacedOnFilter implements DigitalObjectFilter {
    private StringEvaluator _id;

    public PlacedOnFilter(StringEvaluator id) {
        _id = id;
    }

    @Override
    public boolean accept(GameObjects gameObjects, GameActionContext context, DigitalObject object) {
        final String placedOn = object.getAttributes().get("placedOn");
        if (placedOn == null)
            return false;
        return placedOn.equals(_id.getValue(gameObjects, context));
    }
}
