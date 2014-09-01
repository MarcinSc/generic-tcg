package com.gempukku.tcg.generic.card;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;

import java.util.HashMap;
import java.util.Map;

public class CardManager {
    private static final String TYPE = "card";

    public DigitalObject createCard(GameObjects gameObjects, String blueprintId, String owner) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);

        Map<String, String> attrs = new HashMap<String, String>();
        attrs.put("type", TYPE);
        attrs.put("owner", owner);
        attrs.put("blueprintId", blueprintId);

        return digitalEnvironment.createObject(attrs);
    }

    public String getBlueprintId(DigitalObject card) {
        return card.getAttributes().get("blueprintId");
    }
}
