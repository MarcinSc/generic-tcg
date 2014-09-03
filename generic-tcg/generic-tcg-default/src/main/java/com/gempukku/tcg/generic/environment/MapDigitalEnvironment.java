package com.gempukku.tcg.generic.environment;

import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.digital.DigitalObjectListener;
import com.google.common.base.Predicate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapDigitalEnvironment implements DigitalEnvironment {
    private static final char[] UNIQUE_CHARS = "abcdfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final int ID_LENGTH = 4;

    private Map<String, DigitalObjectImpl> _digitalObjects = new HashMap<String, DigitalObjectImpl>();
    private List<DigitalObjectListener> _listeners = new LinkedList<DigitalObjectListener>();

    public void addDigitalObjectListener(DigitalObjectListener listener) {
        _listeners.add(listener);
    }

    public void removeDigitalObjectListener(DigitalObjectListener listener) {
        _listeners.remove(listener);
    }

    public void startFrameChange() {
        for (DigitalObjectListener listener : _listeners)
            listener.startChangeFrame();
    }

    public DigitalObject createObject(Map<String, String> attributes) {
        String newId = generateUniqueId();
        final DigitalObjectImpl digitalObject = new DigitalObjectImpl(newId, attributes);
        _digitalObjects.put(newId, digitalObject);

        for (DigitalObjectListener listener : _listeners)
            listener.objectCreated(newId, attributes);

        return digitalObject;
    }

    public DigitalObject updateObject(String id, Map<String, String> attributeDiff, boolean requiresNewId) {
        String newId = id;
        if (requiresNewId)
            newId = generateUniqueId();

        final DigitalObjectImpl digitalObject = _digitalObjects.get(id);
        digitalObject.updateDigitalObject(newId, attributeDiff);

        for (DigitalObjectListener listener : _listeners)
            listener.objectUpdated(id, newId, attributeDiff);

        return digitalObject;
    }

    public void destroyObject(String id) {
        _digitalObjects.remove(id);

        for (DigitalObjectListener listener : _listeners)
            listener.objectDestroyed(id);
    }

    public void finishFrameChange() {
        for (DigitalObjectListener listener : _listeners)
            listener.finishChangeFrame();
    }

    public DigitalObject getObjectById(String id) {
        return _digitalObjects.get(id);
    }

    public Iterable<DigitalObject> findObjects(Predicate<DigitalObject> predicate) {
        List<DigitalObject> result = new LinkedList<DigitalObject>();
        for (DigitalObject digitalObject : _digitalObjects.values()) {
            if (predicate.apply(digitalObject))
                result.add(digitalObject);
        }
        return result;
    }

    private String generateUniqueId() {
        Random rnd = new Random();
        char[] result = new char[ID_LENGTH];
        String resultStr;
        do {
            for (int i = 0; i < ID_LENGTH; i++)
                result[i] = UNIQUE_CHARS[rnd.nextInt(UNIQUE_CHARS.length)];
            resultStr = new String(result);
        } while (_digitalObjects.containsKey(new String(result)));
        return resultStr;
    }
}
