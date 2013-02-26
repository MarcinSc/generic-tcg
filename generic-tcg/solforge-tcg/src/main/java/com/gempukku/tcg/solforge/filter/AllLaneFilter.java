package com.gempukku.tcg.solforge.filter;

import com.gempukku.tcg.GameState;

public class AllLaneFilter implements LaneFilter {
    @Override
    public boolean matches(GameState gameState, int lane) {
        return true;
    }
}
