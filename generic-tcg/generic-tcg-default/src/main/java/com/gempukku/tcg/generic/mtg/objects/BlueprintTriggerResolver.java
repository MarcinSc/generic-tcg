package com.gempukku.tcg.generic.mtg.objects;

import java.util.List;

public interface BlueprintTriggerResolver {
    public List<TriggerCondition> getTriggerConditions(String blueprint);
}
