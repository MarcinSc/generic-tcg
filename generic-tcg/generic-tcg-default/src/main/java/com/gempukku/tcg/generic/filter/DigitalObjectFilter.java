package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public interface DigitalObjectFilter {
    public boolean matches(GameObjects gameState, DigitalObject context, DigitalObject gameObject);
}
