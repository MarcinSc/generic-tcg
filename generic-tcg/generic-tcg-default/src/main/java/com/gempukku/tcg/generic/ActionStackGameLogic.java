package com.gempukku.tcg.generic;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameLogic;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.actions.Action;

public class ActionStackGameLogic implements GameLogic {
    private ActionStack _actionStack = new DefaultActionStack();
    private Condition _gameFinishCondition;

    public ActionStackGameLogic(Action rootAction, Condition gameFinishCondition) {
        _actionStack.stackAction(rootAction);
        _gameFinishCondition = gameFinishCondition;
    }

    public ActionStack getActionStack() {
        return _actionStack;
    }

    @Override
    public void proceed(GameState gameState, DecisionCallback decisionCallback) {
        _actionStack.processNextStep(gameState, decisionCallback);
    }

    @Override
    public boolean isFinished(GameState gameState, DecisionCallback decisionCallback) {
        return _gameFinishCondition.isTrue(gameState);
    }
}
