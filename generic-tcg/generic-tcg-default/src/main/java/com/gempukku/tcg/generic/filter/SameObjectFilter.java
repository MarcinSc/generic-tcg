package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public class SameObjectFilter implements DigitalObjectFilter {
    @Override
    public boolean matches(GameState gameState, DigitalObject context, DigitalObject gameObject) {
        return context == gameObject;
    }
}
