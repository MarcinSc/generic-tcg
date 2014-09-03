package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.action.GameActionContext;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public class FilterAny implements DigitalObjectFilter {
    @Override
    public boolean accept(GameObjects gameObjects, GameActionContext context, DigitalObject object) {
        return true;
    }
}
