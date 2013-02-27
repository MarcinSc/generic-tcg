package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.action.GameObjectActionSource;

public interface SolforgeTriggerBlueprint {
    public GameObjectActionSource getAfterStackActionSource();

    public GameObjectActionSource getResolveActionSource();
}
