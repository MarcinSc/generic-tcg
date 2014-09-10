package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;

public class FilterAny implements DigitalObjectFilter {
    @Override
    public boolean accept(GameObjects gameObjects, GameEffectContext context, DigitalObject object) {
        return true;
    }
}
