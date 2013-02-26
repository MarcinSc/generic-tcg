package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface GameObjectActionSource {
    public GameAction createGameAction(GameState gameState, GameObject gameObject);
}
