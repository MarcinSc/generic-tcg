package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;

import java.util.Set;

public interface GameStateBuilder {
    public GameState createGameState(Set<String> players);
}
