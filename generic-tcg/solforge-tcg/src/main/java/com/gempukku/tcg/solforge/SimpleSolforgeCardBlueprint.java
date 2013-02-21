package com.gempukku.tcg.solforge;

public class SimpleSolforgeCardBlueprint implements SolforgeCardBlueprint {
    private String _name;

    private String _level1;
    private String _level2;
    private String _level3;

    public void setName(String name) {
        _name = name;
    }

    public void setLevel1(String level1) {
        _level1 = level1;
    }

    public void setLevel2(String level2) {
        _level2 = level2;
    }

    public void setLevel3(String level3) {
        _level3 = level3;
    }

    @Override
    public String getCardLevelBlueprintId(int level) {
        if (level == 1)
            return _level1;
        else if (level == 2)
            return _level2;
        else if (level == 3)
            return _level3;
        else
            throw new IllegalArgumentException("Requested invalid level");
    }

    @Override
    public String getName() {
        return _name;
    }
}
