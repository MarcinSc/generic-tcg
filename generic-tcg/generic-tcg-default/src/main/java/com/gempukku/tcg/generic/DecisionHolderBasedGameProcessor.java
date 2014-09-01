package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;

public class DecisionHolderBasedGameProcessor implements GameProcessor {
    private GameFlow _gameFlow;
    private String _decisionHolderType;

    public void setGameFlow(GameFlow gameFlow) {
        _gameFlow = gameFlow;
    }

    public void setDecisionHolderType(String decisionHolderType) {
        _decisionHolderType = decisionHolderType;
    }

    @Override
    public void playerSentDecision(GameState gameState, String player, String decision) {
        final DecisionHolder decisionHolder = (DecisionHolder) gameState.getGameObject(_decisionHolderType);
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
        final DecisionHolder decisionHolder = (DecisionHolder) gameState.getGameObject(_decisionHolderType);
        do {
            _gameFlow.processGameState(gameState);
        } while (!decisionHolder.hasDecisions(gameState));
    }
}
