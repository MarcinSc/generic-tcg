package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.predicate.FlagPredicate;
import com.gempukku.tcg.generic.predicate.OwnerPredicate;
import com.gempukku.tcg.generic.predicate.TypePredicate;
import com.google.common.base.Predicates;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DigitalObjects {
    public static Iterable<DigitalObject> extractObjects(GameObjects gameObjects, String type) {
        final Iterable<DigitalObject> iterable = getObjectIterator(gameObjects, type);
        if (!iterable.iterator().hasNext())
            return null;
        return iterable;
    }

    public static DigitalObject extractObject(GameObjects gameObjects, String type) {
        final Iterator<DigitalObject> iterator = getObjectIterator(gameObjects, type).iterator();
        if (!iterator.hasNext())
            return null;
        final DigitalObject result = iterator.next();
        if (iterator.hasNext())
            throw new IllegalStateException("More than one object of the type available");
        return result;
    }

    public static DigitalObject extractPlayerObject(GameObjects gameObjects, String type, String player) {
        final Iterator<DigitalObject> iterator = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT).findObjects(
                Predicates.and(new TypePredicate(type), new OwnerPredicate(player))).iterator();
        if (!iterator.hasNext())
            return null;
        final DigitalObject result = iterator.next();
        if (iterator.hasNext())
            throw new IllegalStateException("More than one object of the type available for player: " + player);
        return result;
    }

    public static String getSimpleFlag(GameObjects gameObjects, String flag) {
        final Iterator<DigitalObject> iterator = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT).findObjects(
                Predicates.and(new TypePredicate("flag"), new FlagPredicate(flag))).iterator();
        if (!iterator.hasNext())
            return null;
        final DigitalObject result = iterator.next();
        if (iterator.hasNext())
            throw new IllegalStateException("More than one flag of the type available");
        return result.getAttributes().get("value");
    }

    public static void setSimpleFlag(GameObjects gameObjects, String flag, String value) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final Iterator<DigitalObject> iterator = digitalEnvironment.findObjects(
                Predicates.and(new TypePredicate("flag"), new FlagPredicate(flag))).iterator();
        if (!iterator.hasNext()) {
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("type", "flag");
            attrs.put("flag", flag);
            attrs.put("value", value);
            digitalEnvironment.createObject(attrs);
        } else {
            final DigitalObject result = iterator.next();
            if (iterator.hasNext())
                throw new IllegalStateException("More than one flag of the type available");

            digitalEnvironment.updateObject(result.getId(), Collections.singletonMap("value", value), false);
        }
    }

    public static void removeSimpleFlag(GameObjects gameObjects, String flag) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final Iterator<DigitalObject> iterator = digitalEnvironment.findObjects(
                Predicates.and(new TypePredicate("flag"), new FlagPredicate(flag))).iterator();
        if (iterator.hasNext()) {
            final DigitalObject result = iterator.next();
            if (iterator.hasNext())
                throw new IllegalStateException("More than one flag of the type available");

            digitalEnvironment.destroyObject(result.getId());
        }
    }

    private static Iterable<DigitalObject> getObjectIterator(GameObjects gameObjects, String type) {
        return GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT).findObjects(
                new TypePredicate(type));
    }
}
