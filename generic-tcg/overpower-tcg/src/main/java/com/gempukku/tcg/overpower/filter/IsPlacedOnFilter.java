package com.gempukku.tcg.overpower.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;

public class IsPlacedOnFilter implements DigitalObjectFilter {
    @Override
    public boolean accept(GameObjects gameObjects, GameEffectContext context, DigitalObject object) {
        return object.getAttributes().get("placedOn") != null;
    }
}
