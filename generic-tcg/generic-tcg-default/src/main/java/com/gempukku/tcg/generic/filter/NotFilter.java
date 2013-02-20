package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class NotFilter implements GameObjectFilter {
    private GameObjectFilter _filter;

    public void setFilter(GameObjectFilter filter) {
        _filter = filter;
    }

    @Override
    public boolean matches(GameState gameState, GameObject gameObject) {
        return !_filter.matches(gameState, gameObject);
    }
}
