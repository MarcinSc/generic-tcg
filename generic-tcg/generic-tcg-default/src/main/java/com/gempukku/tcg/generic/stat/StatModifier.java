package com.gempukku.tcg.generic.stat;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.modifier.GameModifier;
import com.gempukku.tcg.generic.object.GameObject;

public interface StatModifier extends GameModifier {
    public int applyModifier(GameState gameState, GameObject gameObject, int value);
}
