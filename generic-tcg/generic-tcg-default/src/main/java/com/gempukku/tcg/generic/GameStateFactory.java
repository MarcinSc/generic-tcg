package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;

public interface GameStateFactory {
    public GameState createNewGameState(String format);
}
