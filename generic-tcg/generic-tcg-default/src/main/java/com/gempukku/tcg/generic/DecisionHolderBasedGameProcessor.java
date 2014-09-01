package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;

public class DecisionHolderBasedGameProcessor implements GameProcessor {
    private GameFlow _gameFlow;

    public void setGameFlow(GameFlow gameFlow) {
        _gameFlow = gameFlow;
    }

    @Override
    public void playerSentDecision(GameState gameState, String player, String decision) {
        final DecisionHolder decisionHolder = GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DECISION_HOLDER);
        final AwaitingDecision awaitingDecision = decisionHolder.removeDecision(gameState, player);
        if (awaitingDecision != null) {
            try {
                awaitingDecision.processAnswer(decision);
                processGameState(gameState);
            } catch (InvalidAnswerException exp) {
                // Put it back in
                decisionHolder.setDecision(gameState, player, awaitingDecision);
            }
        }
    }

    private void processGameState(GameState gameState) {
        final DecisionHolder decisionHolder = GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DECISION_HOLDER);
        do {
            _gameFlow.processGameState(gameState);
        } while (!decisionHolder.hasDecisions(gameState));
    }
}
