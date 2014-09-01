package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;

public class DecisionHolderBasedGameProcessor implements GameProcessor {
    private GameFlow _gameFlow;

    public void setGameFlow(GameFlow gameFlow) {
        _gameFlow = gameFlow;
    }

    @Override
    public void playerSentDecision(GameObjects gameObjects, String player, String decision) {
        final DecisionHolder decisionHolder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DECISION_HOLDER);
        final AwaitingDecision awaitingDecision = decisionHolder.removeDecision(gameObjects, player);
        if (awaitingDecision != null) {
            try {
                awaitingDecision.processAnswer(decision);
                processGameState(gameObjects);
            } catch (InvalidAnswerException exp) {
                // Put it back in
                decisionHolder.setDecision(gameObjects, player, awaitingDecision);
            }
        }
    }

    protected void processGameState(GameObjects gameObjects) {
        final DecisionHolder decisionHolder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DECISION_HOLDER);
        do {
            _gameFlow.processGameState(gameObjects);
        } while (!decisionHolder.hasDecisions(gameObjects));
    }
}
