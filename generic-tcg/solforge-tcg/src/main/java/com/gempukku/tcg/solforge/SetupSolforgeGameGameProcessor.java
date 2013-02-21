package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.PlayerDeckGameProcessor;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.YesNoDecision;
import org.apache.commons.lang.StringUtils;

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
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.TURN_PHASE).setValue("beforeStartOfTurn");

        List<String> players = new ArrayList<String>(gameDeckMap.keySet());

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
