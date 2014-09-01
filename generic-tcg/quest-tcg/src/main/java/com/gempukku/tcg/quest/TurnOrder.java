package com.gempukku.tcg.quest;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TurnOrder implements  GameStateInitializing {
    private static final String TYPE = "turnOrder";

    @Override
    public void setupGameState(GameState gameState) {
        if (DigitalObjects.extractGameObject(gameState, TYPE) == null) {
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("type", TYPE);
            QuestContextObjects.extractGameObject(gameState, QuestContextObjects.DIGITAL_ENVIRONMENT).createObject(attributes);
        }
    }

    public void setTurnOrder(GameState gameState, String... player) {
        final DigitalEnvironment digitalEnvironment = QuestContextObjects.extractGameObject(gameState, QuestContextObjects.DIGITAL_ENVIRONMENT);

        final DigitalObject turnOrder = DigitalObjects.extractGameObject(gameState, TYPE);
        digitalEnvironment.updateObject(turnOrder.getId(), Collections.singletonMap("order", StringUtils.join(player)), false);
    }
}
