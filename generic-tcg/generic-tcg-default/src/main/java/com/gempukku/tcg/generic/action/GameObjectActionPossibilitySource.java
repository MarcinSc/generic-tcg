package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectActionPossibilitySource {
    public boolean isPlayable(GameObjects gameState, DigitalObject fromObject);

    public GameActionPossibility getGameActionPossibility(GameObjects gameState, DigitalObject fromObject);
}
