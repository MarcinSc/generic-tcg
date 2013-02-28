package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface PlayerFilter {
    public boolean matches(GameState gameState, GameObject context, String player);
}
