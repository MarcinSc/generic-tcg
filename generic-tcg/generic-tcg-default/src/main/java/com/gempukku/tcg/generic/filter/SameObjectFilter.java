package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public class SameObjectFilter implements DigitalObjectFilter {
    @Override
    public boolean matches(GameObjects gameState, DigitalObject context, DigitalObject gameObject) {
        return context == gameObject;
    }
}
