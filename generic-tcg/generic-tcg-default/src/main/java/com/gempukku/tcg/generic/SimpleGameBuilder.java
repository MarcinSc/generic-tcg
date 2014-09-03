package com.gempukku.tcg.generic;

import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;

public class SimpleGameBuilder implements GameBuilder {
    private GameObjects _gameObjects;
    private GameProcessor _gameProcessor;
    private DigitalEnvironment _digitalEnvironment;

    public SimpleGameBuilder(GameObjects gameObjects, GameProcessor gameProcessor, DigitalEnvironment digitalEnvironment) {
        _gameObjects = gameObjects;
        _gameProcessor = gameProcessor;
        _digitalEnvironment = digitalEnvironment;
    }

    @Override
    public GameProcessor getGameProcessor() {
        return _gameProcessor;
    }

    @Override
    public GameObjects getGameObjects() {
        return _gameObjects;
    }

    @Override
    public DigitalEnvironment getDigitalEnvironment() {
        return _digitalEnvironment;
    }
}
