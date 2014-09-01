package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.google.common.base.Predicates;

import java.util.Iterator;

public class DigitalObjects {
    public static DigitalObject extractFirstObject(GameState gameState, String type) {
        final Iterator<DigitalObject> iterator = getObjectIterator(gameState, type);
        if (!iterator.hasNext())
            return null;
        return iterator.next();
    }

    public static DigitalObject extractObject(GameState gameState, String type) {
        final Iterator<DigitalObject> iterator = getObjectIterator(gameState, type);
        if (!iterator.hasNext())
            return null;
        final DigitalObject result = iterator.next();
        if (iterator.hasNext())
            throw new IllegalStateException("More than one object of the type available");
        return result;
    }

    public static DigitalObject extractPlayerObject(GameState gameState, String type, String player) {
        final Iterator<DigitalObject> iterator = GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DIGITAL_ENVIRONMENT).findObjects(
                Predicates.and(new ObjectTypePredicate(type), new ObjectOwnerPredicate(player))).iterator();
        if (!iterator.hasNext())
            return null;
        final DigitalObject result = iterator.next();
        if (iterator.hasNext())
            throw new IllegalStateException("More than one object of the type available for player: " + player);
        return result;
    }

    private static Iterator<DigitalObject> getObjectIterator(GameState gameState, String type) {
        return GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DIGITAL_ENVIRONMENT).findObjects(
                new ObjectTypePredicate(type)).iterator();
    }
}
