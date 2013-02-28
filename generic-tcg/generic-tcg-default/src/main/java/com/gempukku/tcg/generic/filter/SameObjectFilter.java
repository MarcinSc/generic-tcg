package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class SameObjectFilter implements GameObjectFilter {
    @Override
    public boolean matches(GameState gameState, GameObject context, GameObject gameObject) {
        return context == gameObject;
    }
}
