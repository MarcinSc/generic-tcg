package com.gempukku.tcg.generic.events;

import com.gempukku.tcg.AwaitingDecision;
import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.EventAction;

public class SendDecisionEvent implements Event {
    private String _player;
    private AwaitingDecision _decision;

    public SendDecisionEvent(String player, AwaitingDecision decision) {
        _player = player;
        _decision = decision;
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        decisionCallback.sendDecision(_player, _decision);
    }
}
