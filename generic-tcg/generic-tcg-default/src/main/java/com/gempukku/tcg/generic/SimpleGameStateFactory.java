package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;

import java.util.Map;
import java.util.Set;

public class SimpleGameStateFactory implements GameStateFactory {
    private Map<String, GameStateBuilder> _gameStateBuilderMap;

    public void setGameStateBuilderMap(Map<String, GameStateBuilder> gameStateBuilderMap) {
        _gameStateBuilderMap = gameStateBuilderMap;
    }

    @Override
    public GameState createNewGameState(String format, Set<String> players) {
        return _gameStateBuilderMap.get(format).createGameState(players);
    }
}
