package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.InitializingPlayerDeckGameProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SetupOverpowerGameProcessor extends InitializingPlayerDeckGameProcessor {
    @Override
    protected void afterGameStateInitialized(GameObjects gameState, Map<String, GameDeck> gameDeckMap) {
        List<String> players = new ArrayList<String>(gameDeckMap.keySet());

        Collections.shuffle(players);

        String firstPlayer = players.get(0);
        String secondPlayer = players.get(1);

        GenericContextObjects.extractGameObject(gameState, GenericContextObjects.TURN_ORDER).setTurnOrder(gameState, firstPlayer, secondPlayer);
    }

    @Override
    protected void afterGameLoaded(GameObjects gameObjects) {
        // Do nothing special
    }
}
