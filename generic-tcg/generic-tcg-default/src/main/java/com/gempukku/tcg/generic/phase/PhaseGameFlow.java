package com.gempukku.tcg.generic.phase;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GameFlow;
import com.gempukku.tcg.generic.GenericContextObjects;

import java.util.Map;

public class PhaseGameFlow implements GameFlow {
    private Map<String, GameFlow> _phaseGameFlows;

    public void setPhaseGameFlows(Map<String, GameFlow> phaseGameFlows) {
        _phaseGameFlows = phaseGameFlows;
    }

    @Override
    public void processGameState(GameObjects gameState) {
        PhaseManager phaseManager = GenericContextObjects.extractGameObject(gameState, GenericContextObjects.PHASE_MANAGER);
        String phase = phaseManager.getPhase(gameState);
        _phaseGameFlows.get(phase).processGameState(gameState);
    }
}
