package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;

public interface PlayerFilter {
    public boolean matches(GameState gameState, String player);
}
