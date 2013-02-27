package com.gempukku.tcg.solforge.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.filter.GameObjectFilter;
import com.gempukku.tcg.generic.object.GameObject;

public class CreatureFilter implements GameObjectFilter {
    @Override
    public boolean matches(GameState gameState, GameObject gameObject) {
        return gameObject.getProperty("cardType").equals("creature");
    }
}
