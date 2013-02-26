package com.gempukku.tcg.solforge.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class AllLaneFilter implements LaneFilter {
    @Override
    public boolean matches(GameState gameState, GameObject gameObject, int lane) {
        return true;
    }
}
