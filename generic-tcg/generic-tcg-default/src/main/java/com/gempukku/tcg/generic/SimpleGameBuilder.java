package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;

public class SimpleGameBuilder implements GameBuilder {
    private GameState _gameState;
    private GameProcessor _gameProcessor;

    public SimpleGameBuilder(GameState gameState, GameProcessor gameProcessor) {
        _gameState = gameState;
        _gameProcessor = gameProcessor;
    }

    @Override
    public GameProcessor getGameProcessor() {
        return _gameProcessor;
    }

    @Override
    public GameState getGameState() {
        return _gameState;
    }
}
