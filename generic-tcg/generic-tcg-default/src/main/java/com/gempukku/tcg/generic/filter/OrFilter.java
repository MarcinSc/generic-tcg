package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.List;

public class OrFilter implements GameObjectFilter {
    public List<GameObjectFilter> _filters;

    public void setFilters(List<GameObjectFilter> filters) {
        _filters = filters;
    }

    @Override
    public boolean matches(GameState gameState, GameObject gameObject) {
        for (GameObjectFilter filter : _filters) {
            if (filter.matches(gameState, gameObject))
                return true;
        }
        return false;
    }
}
