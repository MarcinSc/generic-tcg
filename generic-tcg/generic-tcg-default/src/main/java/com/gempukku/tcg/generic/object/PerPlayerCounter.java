package com.gempukku.tcg.generic.object;

import com.gempukku.tcg.PerPlayerObject;

import java.util.Map;

public class PerPlayerCounter implements PerPlayerObject {
    private Map<String, Counter> _values;
    private int _defaultValue;

    public void setDefaultValue(int defaultValue) {
        _defaultValue = defaultValue;
    }

    @Override
    public Object getObject(String player) {
        Counter counter = _values.get(player);
        if (counter == null) {
            counter = new Counter();
            counter.setValue(_defaultValue);
            _values.put(player, counter);
        }
        return counter;
    }
}
