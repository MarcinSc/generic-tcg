package com.gempukku.tcg.generic.phase;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GameStateInitializing;
import com.gempukku.tcg.generic.GenericContextObjects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PhaseManager implements GameStateInitializing{
    private static final String TYPE="phase";

    private String _initialPhase;

    public void setInitialPhase(String initialPhase) {
        _initialPhase = initialPhase;
    }

    @Override
    public void setupGameState(GameObjects gameState) {
        if (DigitalObjects.extractObject(gameState, TYPE) == null) {
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("type", TYPE);
            attributes.put("phase", _initialPhase);
            GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DIGITAL_ENVIRONMENT).createObject(attributes);
        }
    }

    public String getPhase(GameObjects gameState) {
        return DigitalObjects.extractObject(gameState, TYPE).getAttributes().get("phase");
    }

    public void setPhase(GameObjects gameState, String newPhase) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final DigitalObject phaseObject = DigitalObjects.extractObject(gameState, TYPE);
        digitalEnvironment.updateObject(phaseObject.getId(), Collections.singletonMap("phase", newPhase), false);
    }
}
