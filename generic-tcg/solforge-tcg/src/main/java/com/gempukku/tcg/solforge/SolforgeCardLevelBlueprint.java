package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.action.GameObjectActionPossibilitySource;
import com.gempukku.tcg.generic.action.GameObjectActionSource;

import java.util.List;

public interface SolforgeCardLevelBlueprint {
    public List<GameObjectActionPossibilitySource> getPlayCardActionSources();
    public GameObjectActionSource getResolveActionSource();
    public String getCardType();
}
