package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;

import java.util.Set;

public interface GameStateFactory {
    public GameState createNewGameState(String format, Set<String> players);
}
