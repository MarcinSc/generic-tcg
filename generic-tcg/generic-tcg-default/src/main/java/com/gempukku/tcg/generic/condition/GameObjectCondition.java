package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface GameObjectCondition {
    public boolean isMet(GameState gameState, GameObject gameObject);
}
