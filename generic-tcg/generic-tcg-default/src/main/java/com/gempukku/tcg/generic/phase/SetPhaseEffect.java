package com.gempukku.tcg.generic.phase;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.effect.GameEffect;

public class SetPhaseEffect implements GameEffect {
    private String _value;

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        final PhaseManager phaseManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PHASE_MANAGER);
        phaseManager.setPhase(gameObjects, _value);
        return new Result(null, false);
    }
}
