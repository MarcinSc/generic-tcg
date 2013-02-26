package com.gempukku.tcg.solforge.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.MakeSelectionEffect;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.filter.LaneFilter;

import java.util.List;

public class SolforgeMakeSelectionEffect extends MakeSelectionEffect {
    private LaneFilter _laneFilter;

    public void setLaneFilter(LaneFilter laneFilter) {
        _laneFilter = laneFilter;
    }

    @Override
    protected List<String> getPossibleSelections(GameState gameState, GameObject gameObject) {
        List<String> result = super.getPossibleSelections(gameState, gameObject);
        if (_laneFilter != null)
            appendMatchingLanes(gameState, gameObject, result);
        return result;
    }

    private void appendMatchingLanes(GameState gameState, GameObject gameObject, List<String> result) {
        for (int i=1; i<=5; i++)
            if (_laneFilter.matches(gameState, i))
                result.add("lane:"+i);
    }
}
