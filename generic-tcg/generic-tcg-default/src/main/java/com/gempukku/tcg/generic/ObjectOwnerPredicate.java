package com.gempukku.tcg.generic;

import com.gempukku.tcg.digital.DigitalObject;
import com.google.common.base.Predicate;


public class ObjectOwnerPredicate implements Predicate<DigitalObject> {
    private String _owner;

    public ObjectOwnerPredicate(String owner) {
        _owner = owner;
    }

    @Override
    public boolean apply(DigitalObject input) {
        return input.getAttributes().get("owner").equals(_owner);
    }
}
