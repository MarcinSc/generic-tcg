package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;

public class SimpleGameBuilder implements GameBuilder {
    private GameObjects _gameObjects;
    private GameProcessor _gameProcessor;

    public SimpleGameBuilder(GameObjects gameObjects, GameProcessor gameProcessor) {
        _gameObjects = gameObjects;
        _gameProcessor = gameProcessor;
    }

    @Override
    public GameProcessor getGameProcessor() {
        return _gameProcessor;
    }

    @Override
    public GameObjects getGameObjects() {
        return _gameObjects;
    }
}
