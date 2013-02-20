package com.gempukku.tcg.generic;

import com.gempukku.tcg.PerPlayerObject;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class PerPlayerObjectImpl<T> implements PerPlayerObject<T> {
    private Map<String, T> _objectMap = new HashMap<String, T>();

    @Override
    public Collection<T> getAllObjects() {
        return Collections.synchronizedCollection(_objectMap.values());
    }

    @Override
    public T getObject(String player) {
        T result = _objectMap.get(player);
        if (result == null) {
            result = createInitialObject(player);
            _objectMap.put(player, result);
        }
        return result;
    }

    protected abstract T createInitialObject(String player);
}
