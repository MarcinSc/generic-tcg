package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface GameObjectActionSource {
    public boolean isPlayable(GameState gameState, GameObject fromObject);

    public GameActionPossibility getGameActionPossibility(GameState gameState, GameObject fromObject);
}
