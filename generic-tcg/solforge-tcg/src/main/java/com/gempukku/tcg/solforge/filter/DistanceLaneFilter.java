package com.gempukku.tcg.solforge.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.util.SolforgeObjectUtil;

public class DistanceLaneFilter implements LaneFilter {
    private IntEvaluator _maxDistance;
    private IntEvaluator _minDistance;

    public void setMaxDistance(IntEvaluator maxDistance) {
        _maxDistance = maxDistance;
    }

    public void setMinDistance(IntEvaluator minDistance) {
        _minDistance = minDistance;
    }

    @Override
    public boolean matches(GameState gameState, GameObject gameObject, int lane) {
        final String laneId = gameObject.getProperty("lane");
        int laneNumber = SolforgeObjectUtil.extractLaneNumber(laneId);
        int distanceToLane = Math.abs(lane - laneNumber);
        if (_maxDistance != null && _maxDistance.getValue(gameState, gameObject) < distanceToLane)
            return false;
        if (_minDistance != null && _minDistance.getValue(gameState, gameObject) > distanceToLane)
            return false;
        return true;
    }
}
