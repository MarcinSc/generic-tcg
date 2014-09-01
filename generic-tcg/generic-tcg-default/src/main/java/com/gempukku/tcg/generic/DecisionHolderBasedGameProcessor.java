package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;

import java.util.Map;

public class DecisionHolderBasedGameProcessor implements GameProcessor {
    private GameFlow _gameFlow;

    public void setGameFlow(GameFlow gameFlow) {
        _gameFlow = gameFlow;
    }

    @Override
    public void playerSentDecision(GameObjects gameObjects, String player, String decision) {
        final DecisionHolder decisionHolder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DECISION_HOLDER);

        final Map<String, AwaitingDecision> decisionMap = _gameFlow.processGameState(gameObjects);
        if (decisionMap != null) {
            final AwaitingDecision decisionForPlayer = decisionMap.get(player);
            if (decisionForPlayer != null) {
                try {
                    decisionForPlayer.processAnswer(decision);
                    decisionHolder.removeDecision(gameObjects, player);

                    processGameState(gameObjects);
                } catch (InvalidAnswerException exp) {
                    // Have to wait for correct decision
                }
            }
        }
    }

    protected void processGameState(GameObjects gameObjects) {
        final DecisionHolder decisionHolder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DECISION_HOLDER);

        Map<String, AwaitingDecision> decisions;
        do {
            decisions = _gameFlow.processGameState(gameObjects);
        } while (decisions == null);

        for (Map.Entry<String, AwaitingDecision> playerDecision : decisions.entrySet()) {
            decisionHolder.setDecision(gameObjects, playerDecision.getKey(), playerDecision.getValue());
        }
    }
}
