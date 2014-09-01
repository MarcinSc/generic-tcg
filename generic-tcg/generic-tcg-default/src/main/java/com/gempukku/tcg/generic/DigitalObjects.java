package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.google.common.base.Predicates;

public class DigitalObjects {
    public static DigitalObject extractGameObject(GameState gameState, String type) {
        return ((DigitalEnvironment) gameState.getGameObject("digitalEnvironment")).findObjects(
                new ObjectTypePredicate(type)).iterator().next();
    }

    public static DigitalObject extractPlayerObject(GameState gameState, String type, String player) {
        return ((DigitalEnvironment) gameState.getGameObject("digitalEnvironment")).findObjects(
                Predicates.and(new ObjectTypePredicate(type), new ObjectOwnerPredicate(player))).iterator().next();
    }
}
