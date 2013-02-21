package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;

public interface GameActionPossibility {
    public String getText(GameState gameState);

    public String getAttachedObjectId(GameState gameState);

    public GameAction createGameAction(GameState gameState);
}
