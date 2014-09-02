package com.gempukku.tcg.generic.player;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GamePlayerStateInitializing;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PlayerManager implements GamePlayerStateInitializing {
    private static final String TYPE = "player";

    @Override
    public void setupGameState(GameObjects gameObjects, String player) {
        if (DigitalObjects.extractPlayerObject(gameObjects, TYPE, player) == null) {
            Map<String, String> attributes = new HashMap<String, String>();
            attributes.put("type", TYPE);
            attributes.put("owner", player);
            GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT).createObject(attributes);
        }
    }

    public Iterable<String> getPlayerNames(GameObjects gameObjects) {
        return Iterables.transform(DigitalObjects.extractObjects(gameObjects, TYPE),
                new Function<DigitalObject, String>() {
                    @Override
                    public String apply(DigitalObject input) {
                        return input.getAttributes().get("owner");
                    }
                });
    }
}
