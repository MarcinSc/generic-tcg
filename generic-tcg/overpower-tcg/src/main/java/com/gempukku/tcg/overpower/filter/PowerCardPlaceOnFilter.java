package com.gempukku.tcg.overpower.filter;

import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.generic.filter.Filters;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;

import java.util.ArrayList;
import java.util.List;

public class PowerCardPlaceOnFilter implements OverpowerCardBlueprintFilterFactory {
    @Override
    public DigitalObjectFilter createFor(OverpowerCardBlueprint blueprint) {
        List<DigitalObjectFilter> filters = new ArrayList<DigitalObjectFilter>();
        if (blueprint.getEnergy()>0)
            filters.add(new StatAtLeastFilter("energy", blueprint.getEnergy()));
        if (blueprint.getFighting()>0)
            filters.add(new StatAtLeastFilter("fighting", blueprint.getFighting()));
        if (blueprint.getStrength()>0)
            filters.add(new StatAtLeastFilter("strength", blueprint.getStrength()));

        if (filters.size() == 1)
            return filters.get(0);

        return Filters.or(filters.toArray(new DigitalObjectFilter[filters.size()]));
    }
}
