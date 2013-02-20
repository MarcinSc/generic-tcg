package com.gempukku.tcg;

public interface GameBuilder {
    public GameState getGameState();

    public GameProcessor getGameProcessor();
}
