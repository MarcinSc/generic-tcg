package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TurnOrder implements GameStateInitializing {
    private static final String TYPE = "turnOrder";

    @Override
    public void setupGameState(GameObjects gameObjects) {
        if (DigitalObjects.extractObject(gameObjects, TYPE) == null) {
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("type", TYPE);
            GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT).createObject(attributes);
        }
    }

    public void setTurnOrder(GameObjects gameObjects, String... player) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);

        final DigitalObject turnOrder = DigitalObjects.extractObject(gameObjects, TYPE);
        digitalEnvironment.updateObject(turnOrder.getId(), Collections.singletonMap("order", StringUtils.join(player)), false);
    }
}
