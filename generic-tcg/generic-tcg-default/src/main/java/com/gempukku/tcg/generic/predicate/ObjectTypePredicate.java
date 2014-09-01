package com.gempukku.tcg.generic.predicate;

import com.gempukku.tcg.digital.DigitalObject;
import com.google.common.base.Predicate;

public class ObjectTypePredicate implements Predicate<DigitalObject> {
    private String _type;

    public ObjectTypePredicate(String type) {
        _type = type;
    }

    @Override
    public boolean apply(DigitalObject input) {
        return input.getAttributes().get("type").equals(_type);
    }
}
