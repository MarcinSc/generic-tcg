package com.gempukku.tcg.solforge.filter;

import com.gempukku.tcg.GameState;

public interface LaneFilter {
    public boolean matches(GameState gameState, int lane);
}
