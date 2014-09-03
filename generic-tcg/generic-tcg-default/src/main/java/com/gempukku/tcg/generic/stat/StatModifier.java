package com.gempukku.tcg.generic.stat;

import com.gempukku.tcg.action.GameActionContext;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.modifier.GameModifier;

public interface StatModifier extends GameModifier {
    public int applyModifier(GameObjects gameObjects, GameActionContext context, int value);
}
