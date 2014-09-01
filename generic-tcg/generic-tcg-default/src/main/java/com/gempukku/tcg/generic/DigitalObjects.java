package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.predicate.ObjectOwnerPredicate;
import com.gempukku.tcg.generic.predicate.ObjectTypePredicate;
import com.google.common.base.Predicates;

import java.util.Iterator;

public class DigitalObjects {
    public static DigitalObject extractFirstObject(GameObjects gameObjects, String type) {
        final Iterator<DigitalObject> iterator = getObjectIterator(gameObjects, type);
        if (!iterator.hasNext())
            return null;
        return iterator.next();
    }

    public static DigitalObject extractObject(GameObjects gameObjects, String type) {
        final Iterator<DigitalObject> iterator = getObjectIterator(gameObjects, type);
        if (!iterator.hasNext())
            return null;
        final DigitalObject result = iterator.next();
        if (iterator.hasNext())
            throw new IllegalStateException("More than one object of the type available");
        return result;
    }

    public static DigitalObject extractPlayerObject(GameObjects gameObjects, String type, String player) {
        final Iterator<DigitalObject> iterator = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT).findObjects(
                Predicates.and(new ObjectTypePredicate(type), new ObjectOwnerPredicate(player))).iterator();
        if (!iterator.hasNext())
            return null;
        final DigitalObject result = iterator.next();
        if (iterator.hasNext())
            throw new IllegalStateException("More than one object of the type available for player: " + player);
        return result;
    }

    private static Iterator<DigitalObject> getObjectIterator(GameObjects gameObjects, String type) {
        return GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT).findObjects(
                new ObjectTypePredicate(type)).iterator();
    }
}
