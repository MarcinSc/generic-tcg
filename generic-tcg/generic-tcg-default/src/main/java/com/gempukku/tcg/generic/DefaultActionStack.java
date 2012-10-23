package com.gempukku.tcg.generic;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.actions.Action;

import java.util.Deque;
import java.util.LinkedList;

public class DefaultActionStack implements ActionStack {
    private Deque<Action> _actions = new LinkedList<Action>();

    @Override
    public void stackAction(Action action) {
        _actions.push(action);
    }

    @Override
    public void processNextStep(GameState gameState, DecisionCallback decisionCallback) {
        Action topMostAction = _actions.peek();
        while (!topMostAction.hasNextStep(this, gameState, decisionCallback)) {
            Action nextAction = topMostAction.getNextAction(this, gameState, decisionCallback);
            _actions.pop();
            if (nextAction != null) {
                _actions.push(nextAction);
                topMostAction = nextAction;
            } else {
                topMostAction = _actions.peek();
            }
        }

        topMostAction.processNextStep(this, gameState, decisionCallback);
    }
}
