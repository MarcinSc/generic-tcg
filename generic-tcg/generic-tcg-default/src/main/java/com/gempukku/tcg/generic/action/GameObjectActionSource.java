package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectActionSource {
    public GameAction createGameAction(GameObjects gameState, DigitalObject digitalObject);
}
