package com.gempukku.tcg.quest;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.generic.PlayerDeckGameProcessor;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.YesNoDecision;
import com.gempukku.tcg.generic.modifier.GameModifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SetupQuestGameGameProcessor implements PlayerDeckGameProcessor {
    private GameProcessor _gameProcessor;

    public void setGameProcessor(GameProcessor gameProcessor) {
        _gameProcessor = gameProcessor;
    }

    @Override
    public void playerSentDecision(GameState gameState, String player, String decision) {
        _gameProcessor.playerSentDecision(gameState, player, decision);
    }

    @Override
    public void startProcessing(final GameState gameState, Object gameObjectsResolver, List<GameModifier> alwaysOnGameModifiers, Map<String, GameDeck> gameDeckMap) {
        List<String> players = new ArrayList<String>(gameDeckMap.keySet());

        for (Object gameObject : gameState.getAllGameObjects()) {
            if (gameObject instanceof GameStateInitializing)
                ((GameStateInitializing) gameObject).setupGameState(gameState);
            
            if (gameObject instanceof GamePlayerStateInitializing)
                for (String player : players)
                    ((GamePlayerStateInitializing) gameObject).setupGameState(gameState, player);
        }

        Collections.shuffle(players);
        final String choosingPlayer = players.get(0);
        final String otherPlayer = players.get(1);

        DecisionHolder decisionHolder = QuestContextObjects.extractGameObject(gameState, QuestContextObjects.DECISION_HOLDER);
        decisionHolder.setDecision(gameState, choosingPlayer,
                new YesNoDecision("Would you like to start?") {
                    @Override
                    protected void yes() {
                        setPlayerOrder(gameState, choosingPlayer, otherPlayer);
                    }

                    @Override
                    protected void no() {
                        setPlayerOrder(gameState, otherPlayer, choosingPlayer);
                    }
                });
    }

    private void setPlayerOrder(GameState gameState, String... playerOrder) {
        final TurnOrder turnOrder = QuestContextObjects.extractGameObject(gameState, QuestContextObjects.TURN_ORDER);
        turnOrder.setTurnOrder(gameState, playerOrder);
    }
}
