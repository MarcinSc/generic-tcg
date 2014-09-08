package com.gempukku.tcg.generic.order;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GameStateInitializing;
import com.gempukku.tcg.generic.GenericContextObjects;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlayerOrder implements GameStateInitializing {
    private static final String TYPE = "playerOrder";

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
        digitalEnvironment.updateObject(turnOrder.getId(), Collections.singletonMap("order", StringUtils.join(player, ",")), false);
    }

    public String getFirstPlayer(GameObjects gameObjects) {
        final DigitalObject turnOrder = DigitalObjects.extractObject(gameObjects, TYPE);
        return com.gempukku.tcg.generic.util.StringUtils.correctSplit(turnOrder.getAttributes().get("order"), ",")[0];
    }

    public String getNextPlayerAfter(GameObjects gameObjects, String player) {
        final DigitalObject turnOrder = DigitalObjects.extractObject(gameObjects, TYPE);
        ArrayList<String> players = new ArrayList<String>(Arrays.asList(com.gempukku.tcg.generic.util.StringUtils.correctSplit(turnOrder.getAttributes().get("order"), ",")));
        final int index = players.indexOf(player);
        if (index + 1 == players.size())
            return players.get(0);
        else
            return players.get(index + 1);
    }
}
