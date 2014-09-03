package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.google.common.base.Predicate;

public class PredicateFilter implements DigitalObjectFilter {
    private Predicate<DigitalObject> _predicate;

    public PredicateFilter(Predicate<DigitalObject> predicate) {
        _predicate = predicate;
    }

    @Override
    public boolean accept(GameObjects gameObjects, GameActionContext context, DigitalObject object) {
        return _predicate.apply(object);
    }
}
