package com.gempukku.tcg.solforge.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface LaneFilter {
    public boolean matches(GameState gameState, GameObject gameObject, int lane);
}
