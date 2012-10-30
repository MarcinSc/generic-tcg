package com.gempukku.tcg.quest;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.actions.Action;
import com.gempukku.tcg.generic.decisions.YesNoDecision;
import com.gempukku.tcg.generic.mtg.MtgConstants;
import com.gempukku.tcg.generic.mtg.objects.TurnOrder;

public class QuestGameLoopAction implements Action {
    @Override
    public Action getNextAction(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        return null;
    }

    @Override
    public boolean hasNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        return true;
    }

    @Override
    public void processNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        TurnOrder turnOrder = (TurnOrder) gameState.getGameObject(MtgConstants.TURN_ORDER);
        String player = turnOrder.nextTurn();
        decisionCallback.sendDecision(player,
                new YesNoDecision("It is your turn"));
    }
}
