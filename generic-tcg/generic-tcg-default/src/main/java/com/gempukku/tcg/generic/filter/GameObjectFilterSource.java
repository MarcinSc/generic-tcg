package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface GameObjectFilterSource {
    public GameObjectFilter createGameObjectFilter(GameState gameState, GameObject gameObject);
}
