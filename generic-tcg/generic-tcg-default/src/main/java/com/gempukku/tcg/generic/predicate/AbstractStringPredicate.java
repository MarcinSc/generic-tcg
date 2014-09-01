package com.gempukku.tcg.generic.predicate;

import com.gempukku.tcg.digital.DigitalObject;
import com.google.common.base.Predicate;

public abstract class AbstractStringPredicate implements Predicate<DigitalObject> {
    private String _key;
    private String _value;

    protected AbstractStringPredicate(String key, String value) {
        _key = key;
        _value = value;
    }

    @Override
    public boolean apply(DigitalObject input) {
        return input.getAttributes().get(_key).equals(_value);
    }
}
