package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectActionPossibilitySource {
    public boolean isPlayable(GameState gameState, DigitalObject fromObject);

    public GameActionPossibility getGameActionPossibility(GameState gameState, DigitalObject fromObject);
}
