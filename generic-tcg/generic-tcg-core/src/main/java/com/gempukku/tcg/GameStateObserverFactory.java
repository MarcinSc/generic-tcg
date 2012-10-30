package com.gempukku.tcg;

public interface GameStateObserverFactory<T> {
    public T createGameStateObserver(String player);
}
