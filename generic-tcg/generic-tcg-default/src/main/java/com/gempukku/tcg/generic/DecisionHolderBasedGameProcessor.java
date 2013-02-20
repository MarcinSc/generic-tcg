package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.PerPlayerObject;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;

public class DecisionHolderBasedGameProcessor implements GameProcessor {
    private PerPlayerObject<DecisionHolder> _decisionHolderPerPlayer;
    private GameFlow _gameFlow;

    public void setDecisionHolderPerPlayer(PerPlayerObject<DecisionHolder> decisionHolderPerPlayer) {
        _decisionHolderPerPlayer = decisionHolderPerPlayer;
    }

    public void setGameFlow(GameFlow gameFlow) {
        _gameFlow = gameFlow;
    }

    @Override
    public void playerSentDecision(GameState gameState, String player, String decision) {
        final DecisionHolder decisionHolder = _decisionHolderPerPlayer.getObject(player);
        final AwaitingDecision awaitingDecision = decisionHolder.removeDecision();
        if (awaitingDecision != null) {
            try {
                awaitingDecision.processAnswer(decision);
                processGameState(gameState);
            } catch (InvalidAnswerException exp) {
                // Put it back in
                decisionHolder.setDecision(awaitingDecision);
            }
        }
    }

    private void processGameState(GameState gameState) {
        do {
            _gameFlow.processGameState(gameState);
        } while (!hasAwaitingDecisions());
    }

    private boolean hasAwaitingDecisions() {
        for (DecisionHolder decisionHolder : _decisionHolderPerPlayer.getAllObjects()) {
            if (decisionHolder.getDecision() != null)
                return true;
        }
        return false;
    }
}
