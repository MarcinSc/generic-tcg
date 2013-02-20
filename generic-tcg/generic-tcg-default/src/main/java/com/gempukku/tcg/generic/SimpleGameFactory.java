package com.gempukku.tcg.generic;

import java.util.Map;
import java.util.Set;

public class SimpleGameFactory implements GameFactory {
    private Map<String, GameBuilderFactory> _gameBuilderMap;

    public void setGameBuilderMap(Map<String, GameBuilderFactory> gameBuilderMap) {
        _gameBuilderMap = gameBuilderMap;
    }

    @Override
    public GameBuilder createNewGameBuilder(String format, Set<String> players) {
        return _gameBuilderMap.get(format).createGameBuilder(players);
    }
}
