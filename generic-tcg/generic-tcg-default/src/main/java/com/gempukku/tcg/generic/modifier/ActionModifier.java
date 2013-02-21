package com.gempukku.tcg.generic.modifier;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;

import java.util.Collection;

public interface ActionModifier extends GameModifier {
    public Collection<GameAction> getPossibleActions(GameState gameState);
}
