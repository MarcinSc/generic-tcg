package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;

public interface GameActionPossibility {
    public String getText(GameObjects gameObjects);

    public String getAttachedObjectId(GameObjects gameObjects);

    public GameAction createGameAction(GameObjects gameObjects);
}
