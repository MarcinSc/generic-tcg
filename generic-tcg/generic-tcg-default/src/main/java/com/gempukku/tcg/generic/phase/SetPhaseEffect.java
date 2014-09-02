package com.gempukku.tcg.generic.phase;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.effect.GameObjectEffectSerie;

public class SetPhaseEffect implements GameObjectEffectSerie {
    private String _value;

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public Result execute(GameObjects gameObjects, DigitalObject context) {
        final PhaseManager phaseManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PHASE_MANAGER);
        phaseManager.setPhase(gameObjects, _value);
        return new Result(null, false);
    }
}
