package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class InitializingPlayerDeckGameProcessor extends DecisionHolderBasedGameProcessor implements PlayerDeckGameProcessor {
    @Override
    public void startProcessing(GameState gameState, Map<String, GameDeck> gameDeckMap) {
        List<String> players = new ArrayList<String>(gameDeckMap.keySet());

        for (Object gameObject : gameState.getAllGameObjects()) {
            if (gameObject instanceof GameStateInitializing)
                ((GameStateInitializing) gameObject).setupGameState(gameState);

            if (gameObject instanceof GamePlayerStateInitializing)
                for (String player : players)
                    ((GamePlayerStateInitializing) gameObject).setupGameState(gameState, player);
        }

    }

    protected abstract void afterGameStateInitialized(GameState gameState, Map<String, GameDeck> gameDeckMap);
}
