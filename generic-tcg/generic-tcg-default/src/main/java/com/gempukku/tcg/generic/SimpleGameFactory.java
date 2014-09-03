package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameFactory;

import java.util.Map;

public class SimpleGameFactory implements GameFactory {
    private Map<String, GameBuilderFactory> _gameBuilderMap;

    public void setGameBuilderMap(Map<String, GameBuilderFactory> gameBuilderMap) {
        _gameBuilderMap = gameBuilderMap;
    }

    @Override
    public GameBuilder createNewGameBuilder(String format, Map<String, GameDeck> playersAndDecks) {
        return _gameBuilderMap.get(format).startNewGame(playersAndDecks);
    }
}
