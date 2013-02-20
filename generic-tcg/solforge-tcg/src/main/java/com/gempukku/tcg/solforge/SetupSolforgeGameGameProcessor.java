package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.PlayerDeckGameProcessor;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.YesNoDecision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SetupSolforgeGameGameProcessor implements PlayerDeckGameProcessor {
    private GameProcessor _gameProcessor;

    public void setGameProcessor(GameProcessor gameProcessor) {
        _gameProcessor = gameProcessor;
    }

    @Override
    public void playerSentDecision(GameState gameState, String player, String decision) {
        _gameProcessor.playerSentDecision(gameState, player, decision);
    }

    @Override
    public void startProcessing(final GameState gameState, Map<String, GameDeck> gameDeckMap) {
        List<String> players = new ArrayList<String>(gameDeckMap.keySet());

        Collections.shuffle(players);
        final String choosingPlayer = players.get(0);
        final String otherPlayer = players.get(0);

        DecisionHolder decisionHolder = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.DECISION_HOLDER, choosingPlayer);
        decisionHolder.setDecision(
                new YesNoDecision("Would you like to start?") {
                    @Override
                    protected void yes() {
                        setStartingPlayer(gameState, choosingPlayer);
                    }

                    @Override
                    protected void no() {
                        setStartingPlayer(gameState, otherPlayer);
                    }
                }
        );
    }

    private void setStartingPlayer(GameState gameState, String player) {
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_TURN).setValue(player);
    }
}
