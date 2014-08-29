package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public interface DigitalObjectFilter {
    public boolean matches(GameState gameState, DigitalObject context, DigitalObject gameObject);
}
