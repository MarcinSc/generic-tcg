package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameActionStack;

public class GameActionStackFlow implements GameFlow {
    private GameActionStack _gameActionStack;

    public void setGameActionStack(GameActionStack gameActionStack) {
        _gameActionStack = gameActionStack;
    }

    @Override
    public void processGameState(GameState gameState) {
        _gameActionStack.process(gameState);
    }
}
