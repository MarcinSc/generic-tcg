package com.gempukku.tcg.generic.phase;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GameFlow;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.Map;

public class PhaseGameFlow implements GameFlow {
    private Map<String, GameFlow> _phaseGameFlows;

    public void setPhaseGameFlows(Map<String, GameFlow> phaseGameFlows) {
        _phaseGameFlows = phaseGameFlows;
    }

    @Override
    public Map<String, AwaitingDecision> processGameState(GameObjects gameObjects) {
        PhaseManager phaseManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PHASE_MANAGER);
        String phase = phaseManager.getPhase(gameObjects);
        return _phaseGameFlows.get(phase).processGameState(gameObjects);
    }
}
