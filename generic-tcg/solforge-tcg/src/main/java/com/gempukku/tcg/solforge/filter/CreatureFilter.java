package com.gempukku.tcg.solforge.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.filter.GameObjectFilter;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.Solforge;

public class CreatureFilter implements GameObjectFilter {
    @Override
    public boolean matches(GameState gameState, GameObject gameObject) {
        return gameObject.getProperty(Solforge.Properties.CARD_TYPE).equals("creature");
    }
}
