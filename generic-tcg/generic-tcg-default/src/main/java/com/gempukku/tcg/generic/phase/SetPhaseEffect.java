package com.gempukku.tcg.generic.phase;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;

public class SetPhaseEffect implements GameEffect {
    private String _value;

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final PhaseManager phaseManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PHASE_MANAGER);
        phaseManager.setPhase(gameObjects, _value);
        return Result.pass();
    }
}
