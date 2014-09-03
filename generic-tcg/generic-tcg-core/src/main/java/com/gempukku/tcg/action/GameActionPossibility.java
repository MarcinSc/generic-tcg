package com.gempukku.tcg.action;

import com.gempukku.tcg.GameObjects;

public interface GameActionPossibility {
    public String getText(GameObjects gameObjects);

    public String getAttachedObjectId(GameObjects gameObjects);

    public GameAction createGameAction(GameObjects gameObjects);
}
