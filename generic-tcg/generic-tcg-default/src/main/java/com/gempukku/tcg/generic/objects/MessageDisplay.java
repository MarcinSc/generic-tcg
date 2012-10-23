package com.gempukku.tcg.generic.objects;

import java.util.ArrayList;
import java.util.List;

public class MessageDisplay {
    private List<Message> _messages = new ArrayList<Message>();

    public void addMessage(Message message) {
        _messages.add(message);
    }

    public int getMessageCount() {
        return _messages.size();
    }

    public Message getMessage(int index) {
        return _messages.get(index);
    }
}
