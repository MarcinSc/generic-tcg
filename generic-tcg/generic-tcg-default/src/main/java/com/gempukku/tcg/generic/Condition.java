package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;

public interface Condition {
    public boolean isTrue(GameState gameState);
}
