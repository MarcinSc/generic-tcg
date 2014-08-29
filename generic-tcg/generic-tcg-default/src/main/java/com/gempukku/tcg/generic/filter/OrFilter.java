package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

import java.util.List;

public class OrFilter implements DigitalObjectFilter {
    public List<DigitalObjectFilter> _filters;

    public void setFilters(List<DigitalObjectFilter> filters) {
        _filters = filters;
    }

    @Override
    public boolean matches(GameState gameState, DigitalObject context, DigitalObject gameObject) {
        for (DigitalObjectFilter filter : _filters) {
            if (filter.matches(gameState, context, gameObject))
                return true;
        }
        return false;
    }
}
