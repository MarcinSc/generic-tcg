package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;

import java.util.LinkedList;
import java.util.Queue;

public class GameActionStack {
    private Queue<GameAction> _gameActionStack = new LinkedList<GameAction>();

    public void setRootGameAction(GameAction gameAction) {
        _gameActionStack.offer(gameAction);
    }

    private GameAction getTopMostUnfinishedAction(GameState gameState) {
        GameAction topMostAction;
        while ((topMostAction = _gameActionStack.peek()) != null) {
            if (!topMostAction.hasNextGameEffect(gameState))
                _gameActionStack.remove();
            else
                return topMostAction;
        }
        throw new IllegalStateException("Stack should not ever finish");
    }

    public void process(GameState gameState) {
        GameAction gameAction = getTopMostUnfinishedAction(gameState);
        gameAction.processNextGameEffect(gameState);
    }
}
