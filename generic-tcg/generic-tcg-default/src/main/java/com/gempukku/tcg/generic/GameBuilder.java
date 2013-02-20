package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;

public interface GameBuilder {
    public GameState getGameState();

    public GameProcessor getGameProcessor();
}
