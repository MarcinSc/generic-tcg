package com.gempukku.tcg.generic.modifier;

import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.gempukku.tcg.GameObjects;

import java.util.Collection;

public interface ActionModifier extends GameModifier {
    public Collection<GameActionPossibility> getPossibleActions(GameObjects gameState);
}
