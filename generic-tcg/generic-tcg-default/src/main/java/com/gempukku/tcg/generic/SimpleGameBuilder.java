package com.gempukku.tcg.generic;

import com.gempukku.tcg.decision.DecisionHolder;
import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;

public class SimpleGameBuilder implements GameBuilder {
    private GameObjects _gameObjects;
    private GameProcessor _gameProcessor;
    private DecisionHolder _decisionHolder;

    public SimpleGameBuilder(GameObjects gameObjects, GameProcessor gameProcessor, DecisionHolder decisionHolder) {
        _gameObjects = gameObjects;
        _gameProcessor = gameProcessor;
        _decisionHolder = decisionHolder;
    }

    @Override
    public GameProcessor getGameProcessor() {
        return _gameProcessor;
    }

    @Override
    public GameObjects getGameObjects() {
        return _gameObjects;
    }

    public DecisionHolder getDecisionHolder() {
        return _decisionHolder;
    }
}
