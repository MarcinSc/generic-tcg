package com.gempukku.tcg.generic.actions;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Condition;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

public class LoopAction implements Action {
    private int _index;
    private List<Action> _actions;
    private Condition _loopFinishCondition;
    private Action _nextAction;

    @Required
    public void setActions(List<Action> actions) {
        _actions = actions;
    }

    @Required
    public void setLoopFinishCondition(Condition loopFinishCondition) {
        _loopFinishCondition = loopFinishCondition;
    }

    public void setNextAction(Action nextAction) {
        _nextAction = nextAction;
    }

    @Override
    public void processNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        _actions.get(_index).processNextStep(actionStack, gameState, decisionCallback);
    }

    @Override
    public boolean hasNextStep(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        while (!_actions.get(_index).hasNextStep(actionStack, gameState, decisionCallback)) {
            _index++;
            if (_index == _actions.size()) {
                if (_loopFinishCondition.isTrue(gameState))
                    return false;
                else
                    _index = 0;
            }
        }
        return true;
    }

    @Override
    public Action getNextAction(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        return _nextAction;
    }
}
