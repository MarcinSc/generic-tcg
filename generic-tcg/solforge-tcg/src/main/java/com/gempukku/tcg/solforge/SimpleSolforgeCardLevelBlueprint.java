package com.gempukku.tcg.solforge;

import java.util.List;

public class SimpleSolforgeCardLevelBlueprint implements SolforgeCardLevelBlueprint {
    private List<String> _playCardActionIds;

    public void setPlayCardActionIds(List<String> playCardActionIds) {
        _playCardActionIds = playCardActionIds;
    }

    @Override
    public List<String> getPlayCardActionIds() {
        return _playCardActionIds;
    }
}
