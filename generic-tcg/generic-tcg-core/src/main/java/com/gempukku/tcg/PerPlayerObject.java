package com.gempukku.tcg;

import java.util.Collection;

public interface PerPlayerObject<T> {
    public T getObject(String player);

    public Collection<T> getAllObjects();
}
