package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;

public interface GameActionPossibility {
    public String getText(GameObjects gameState);

    public String getAttachedObjectId(GameObjects gameState);

    public GameAction createGameAction(GameObjects gameState);
}
