package com.gempukku.tcg.overpower.filter.factory;

import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;

public interface OverpowerCardBlueprintFilterFactory {
    public DigitalObjectFilter createFor(OverpowerCardBlueprint blueprint);
}
