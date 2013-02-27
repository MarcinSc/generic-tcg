package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.action.GameObjectActionPossibilitySource;
import com.gempukku.tcg.generic.action.GameObjectActionSource;
import com.gempukku.tcg.generic.keyword.Keyword;
import com.gempukku.tcg.solforge.trigger.TriggeredEffect;

import java.util.List;

public interface SolforgeCardLevelBlueprint {
    public List<GameObjectActionPossibilitySource> getPlayCardActionSources();

    public GameObjectActionSource getResolveActionSource();

    public String getCardType();

    public List<Keyword> getKeywords();

    public int getAttack();

    public int getHealth();

    public List<TriggeredEffect> getTriggeredEffects();
}
