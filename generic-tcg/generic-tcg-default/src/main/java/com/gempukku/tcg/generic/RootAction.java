package com.gempukku.tcg.generic;

public interface RootAction<T> {
    public void proceed(T gameState, DecisionCallback decisionCallback);
    public boolean hasMore(T gameState);
    public RootAction<T> getNext(T gameState);
}
