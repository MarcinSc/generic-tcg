package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;

public interface GamePlayerStateInitializing {
    public void setupGameState(GameState gameState, String player);
}
