package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public class NotFilter implements DigitalObjectFilter {
    private DigitalObjectFilter _filter;

    public void setFilter(DigitalObjectFilter filter) {
        _filter = filter;
    }

    @Override
    public boolean matches(GameObjects gameState, DigitalObject context, DigitalObject gameObject) {
        return !_filter.matches(gameState, context, gameObject);
    }
}
