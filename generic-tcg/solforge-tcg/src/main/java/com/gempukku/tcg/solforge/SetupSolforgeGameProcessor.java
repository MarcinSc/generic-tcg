package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.PlayerDeckGameProcessor;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.YesNoDecision;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.Zone;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class SetupSolforgeGameProcessor implements PlayerDeckGameProcessor {
    private GameProcessor _gameProcessor;

    public void setGameProcessor(GameProcessor gameProcessor) {
        _gameProcessor = gameProcessor;
    }

    @Override
    public void playerSentDecision(GameState gameState, String player, String decision) {
        _gameProcessor.playerSentDecision(gameState, player, decision);
    }

    @Override
    public void startProcessing(final GameState gameState, Object gameObjectsResolver, Map<String, GameDeck> gameDeckMap) {
        gameState.addGameObject("objectResolver", gameObjectsResolver);

        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.TURN_PHASE).setValue("beforeStartOfTurn");

        List<String> players = new ArrayList<String>();
        for (Map.Entry<String, GameDeck> playerDeck : gameDeckMap.entrySet()) {
            final String player = playerDeck.getKey();
            final GameDeck deck = playerDeck.getValue();

            players.add(player);
            final Zone deckZone = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.DECK_ZONE, player);
            final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER);
            final List<String> mainDeck = deck.getCardBlueprints().get("main");
            for (String cardInDeck : mainDeck) {
                Map<String, String> properties = new HashMap<String, String>();
                properties.put("blueprintId", cardInDeck);
                properties.put("owner", player);
                gameObjectManager.createObjectInZone(deckZone, properties);
            }

            deckZone.shuffle();
        }

        Collections.shuffle(players);
        final String choosingPlayer = players.get(0);
        final String otherPlayer = players.get(0);

        DecisionHolder decisionHolder = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.DECISION_HOLDER, choosingPlayer);
        decisionHolder.setDecision(
                new YesNoDecision("Would you like to start?") {
                    @Override
                    protected void yes() {
                        setPlayerOrder(gameState, choosingPlayer, otherPlayer);
                    }

                    @Override
                    protected void no() {
                        setPlayerOrder(gameState, otherPlayer, choosingPlayer);
                    }
                }
        );
    }

    private void setPlayerOrder(GameState gameState, String... playerOrder) {
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_ORDER).setValue(StringUtils.join(playerOrder, ","));
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_TURN).setValue(playerOrder[0]);
    }
}
