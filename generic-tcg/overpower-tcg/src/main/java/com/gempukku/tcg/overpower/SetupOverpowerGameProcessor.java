package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.InitializingPlayerDeckGameProcessor;
import com.gempukku.tcg.generic.card.CardManager;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SetupOverpowerGameProcessor extends InitializingPlayerDeckGameProcessor {
    @Override
    protected void afterGameStateInitialized(GameObjects gameObjects, Map<String, GameDeck> gameDeckMap) {
        List<String> players = new ArrayList<String>(gameDeckMap.keySet());

        Collections.shuffle(players);

        String firstPlayer = players.get(0);
        String secondPlayer = players.get(1);

        GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_ORDER).setTurnOrder(gameObjects, firstPlayer, secondPlayer);

        final CardManager cardManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.CARD_MANAGER);
        final PlayerDigitalObjectStackManager setupCharacters = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.SETUP_CHARACTERS_ZONE);
        final PlayerDigitalObjectStackManager setupDeck = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.SETUP_DECK_ZONE);

        for (String player : players) {
            final List<String> characters = gameDeckMap.get(player).getCardBlueprints().get("characters");
            for (String character : characters) {
                final DigitalObject card = cardManager.createCard(gameObjects, character, player);
                setupCharacters.putOnTop(gameObjects, player, card);
            }
            final List<String> cardBlueprints = gameDeckMap.get(player).getCardBlueprints().get("deck");
            for (String cardBlueprint : cardBlueprints) {
                final DigitalObject card = cardManager.createCard(gameObjects, cardBlueprint, player);
                setupDeck.putOnTop(gameObjects, player, card);
            }
        }
    }

    @Override
    protected void afterGameLoaded(GameObjects gameObjects) {
        // Do nothing special
    }
}
