package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.generic.decision.DecisionHolderManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class InitializingPlayerDeckGameProcessor extends DecisionHolderBasedGameProcessor implements PlayerDeckGameProcessor {
    @Override
    public void startProcessing(GameObjects gameObjects, Map<String, GameDeck> gameDeckMap) {
        gameObjects.addGameObject("digitalEnvironment", new DigitalEnvironment());
        gameObjects.addGameObject("decisionHolder", new DecisionHolderManager());

        List<String> players = new ArrayList<String>(gameDeckMap.keySet());

        for (Object gameObject : gameObjects.getAllGameObjects()) {
            if (gameObject instanceof GameStateInitializing)
                ((GameStateInitializing) gameObject).setupGameState(gameObjects);

            if (gameObject instanceof GamePlayerStateInitializing)
                for (String player : players)
                    ((GamePlayerStateInitializing) gameObject).setupGameState(gameObjects, player);
        }

        afterGameStateInitialized(gameObjects, gameDeckMap);

        processGameState(gameObjects);
    }

    @Override
    public void startProcessingLoaded(GameObjects gameObjects, DigitalEnvironment environment) {
        gameObjects.addGameObject("digitalEnvironment", environment);
        gameObjects.addGameObject("decisionHolder", new DecisionHolderManager());

        afterGameLoaded(gameObjects);

        processGameState(gameObjects);
    }

    protected abstract void afterGameStateInitialized(GameObjects gameObjects, Map<String, GameDeck> gameDeckMap);

    protected abstract void afterGameLoaded(GameObjects gameObjects);
}