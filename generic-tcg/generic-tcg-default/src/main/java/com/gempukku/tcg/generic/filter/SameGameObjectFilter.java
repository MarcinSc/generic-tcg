package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class SameGameObjectFilter implements GameObjectFilterSource {
    @Override
    public GameObjectFilter createGameObjectFilter(GameState gameState, final GameObject gameObject) {
        return new GameObjectFilter() {
            @Override
            public boolean matches(GameState gameState, GameObject possible) {
                return gameObject == possible;
            }
        };
    }
}
