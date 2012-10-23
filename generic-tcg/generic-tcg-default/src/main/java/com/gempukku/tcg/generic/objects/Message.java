package com.gempukku.tcg.generic.objects;

public class Message {
    private final String _originator;
    private final String _message;

    public Message(String originator, String message) {
        _originator = originator;
        _message = message;
    }

    public String getOriginator() {
        return _originator;
    }

    public String getMessage() {
        return _message;
    }
}
