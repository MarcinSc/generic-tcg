package com.gempukku.tcg;

public interface GameMediatorVisitor<T> {
    public void consumeChanges(T gameStateObserver);
}
