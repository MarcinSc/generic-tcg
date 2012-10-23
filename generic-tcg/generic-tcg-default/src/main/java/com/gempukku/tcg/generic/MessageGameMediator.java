package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameLogic;
import com.gempukku.tcg.GameMediator;
import com.gempukku.tcg.GameStateObserver;
import com.gempukku.tcg.generic.objects.Message;
import com.gempukku.tcg.generic.objects.MessageDisplay;

public class MessageGameMediator<T extends GameStateObserver> extends GameMediator<T> {
    private String _messageDisplay;
    public MessageGameMediator(GameLogic gameLogic, String messageDisplay) {
        super(gameLogic);
        _messageDisplay = messageDisplay;
    }

    public void addMessage(String player, String message) {
        getLock().writeLock().lock();
        try {
            MessageDisplay messageDisplay = (MessageDisplay) getGameState().getGameObject(_messageDisplay);
            messageDisplay.addMessage(new Message(player, message));
        } finally {
            getLock().writeLock().unlock();
        }
    }
}
