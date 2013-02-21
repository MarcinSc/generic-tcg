package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;

import java.util.LinkedList;

public class GameActionStack {
    private LinkedList<GameAction> _gameActionStack = new LinkedList<GameAction>();

    public void setRootGameAction(GameAction gameAction) {
        _gameActionStack.add(gameAction);
    }

    private GameAction getTopMostUnfinishedAction(GameState gameState) {
        GameAction topMostAction;
        while ((topMostAction = _gameActionStack.getLast()) != null) {
            if (!topMostAction.hasNextGameEffect(gameState))
                _gameActionStack.removeLast();
            else
                return topMostAction;
        }
        throw new IllegalStateException("Stack should not ever finish");
    }

    public void stackGameAction(GameAction gameAction) {
        _gameActionStack.add(gameAction);
    }

    public void process(GameState gameState) {
        GameAction gameAction = getTopMostUnfinishedAction(gameState);
        gameAction.processNextGameEffect(gameState);
    }
}
