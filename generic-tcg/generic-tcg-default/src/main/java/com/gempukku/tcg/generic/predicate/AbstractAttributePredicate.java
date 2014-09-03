package com.gempukku.tcg.generic.predicate;

import com.gempukku.tcg.digital.DigitalObject;
import com.google.common.base.Predicate;

public abstract class AbstractAttributePredicate implements Predicate<DigitalObject> {
    private String _name;
    private String _value;

    protected AbstractAttributePredicate(String name, String value) {
        _name = name;
        _value = value;
    }

    @Override
    public boolean apply(DigitalObject input) {
        return _value.equals(input.getAttributes().get(_name));
    }
}
