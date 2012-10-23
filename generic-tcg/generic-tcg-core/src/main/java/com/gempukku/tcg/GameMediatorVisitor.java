package com.gempukku.tcg;

public interface GameMediatorVisitor<T extends GameStateObserver> {
    public void consumeChanges(T gameStateListener);
}
