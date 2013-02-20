package com.gempukku.tcg.generic.object;

import com.gempukku.tcg.generic.PerPlayerObjectImpl;

public class PerPlayerCounter extends PerPlayerObjectImpl<Counter> {
    private int _defaultValue;

    public void setDefaultValue(int defaultValue) {
        _defaultValue = defaultValue;
    }

    @Override
    protected Counter createInitialObject(String player) {
        final Counter counter = new Counter();
        counter.setValue(_defaultValue);
        return counter;
    }
}
