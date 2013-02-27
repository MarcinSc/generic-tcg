package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;

public class AnyPlayerFilter implements PlayerFilter {
    @Override
    public boolean matches(GameState gameState, String player) {
        return true;
    }
}
