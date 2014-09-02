package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.digital.DigitalObjectFilter;

public class FilterAny implements DigitalObjectFilter {
    @Override
    public boolean accept(GameObjects gameObjects, DigitalObject context, DigitalObject object) {
        return true;
    }
}
