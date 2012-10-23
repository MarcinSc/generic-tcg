package com.gempukku.tcg.generic.events;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.EventAction;
import com.gempukku.tcg.generic.objects.Message;
import com.gempukku.tcg.generic.objects.MessageDisplay;

public class SendSystemMessageEvent implements Event {
    private String _messageDisplay;
    private String _message;

    public SendSystemMessageEvent(String messageDisplay, String message) {
        _messageDisplay = messageDisplay;
        _message = message;
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        MessageDisplay messageDisplay = (MessageDisplay) gameState.getGameObject(_messageDisplay);
        messageDisplay.addMessage(new Message("system", _message));
    }
}
