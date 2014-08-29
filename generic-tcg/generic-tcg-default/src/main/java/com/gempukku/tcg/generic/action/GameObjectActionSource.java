package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectActionSource {
    public GameAction createGameAction(GameState gameState, DigitalObject digitalObject);
}
