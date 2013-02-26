package com.gempukku.tcg.solforge;

public interface SolforgeCardBlueprint {
    public SolforgeCardLevelBlueprint getCardLevelBlueprintId(int level);

    public String getName();
}
