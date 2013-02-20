package com.gempukku.tcg.generic.object;

import java.util.*;

public class GameObjectManager {
    private char[] _availableIdCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

    private Random _rnd = new Random();
    private Set<String> _usedIds = new HashSet<String>();

    private boolean _zoneMoveChangesId;
    private List<Zone> _zones;

    public void setZoneMoveChangesId(boolean zoneMoveChangesId) {
        _zoneMoveChangesId = zoneMoveChangesId;
    }

    public void setZones(List<Zone> zones) {
        _zones = zones;
    }

    public GameObject createObjectInZone(Zone zone, Map<String, String> properties) {
        GameObject gameObject = new GameObject();
        final String id = generateNewId();
        _usedIds.add(id);
        gameObject.setIdentifier(id);
        for (Map.Entry<String, String> property : properties.entrySet())
            gameObject.setProperty(property.getKey(), property.getValue());

        zone.addObject(gameObject);

        return gameObject;
    }

    public void destroyObjectInZone(Zone zone, GameObject gameObject) {
        zone.removeObject(gameObject);
        final String id = gameObject.getIdentifier();
        _usedIds.remove(id);
    }

    public void moveObjectBetweenZones(Zone zoneFrom, Zone zoneTo, GameObject gameObject) {
        zoneFrom.removeObject(gameObject);
        if (_zoneMoveChangesId) {
            final String id = gameObject.getIdentifier();
            _usedIds.remove(id);

            final String newId = generateNewId();
            gameObject.setIdentifier(newId);
        }
        zoneTo.addObject(gameObject);
    }

    public void visitGameObjects(GameObjectVisitor gameObjectVisitor) {
        for (Zone zone : _zones) {
            for (GameObject gameObject : zone.getGameObjects()) {
                final boolean stop = gameObjectVisitor.visitGameObject(gameObject);
                if (stop)
                    return;
            }
        }
    }

    private String generateNewId() {
        StringBuilder sb = new StringBuilder();
        String result;
        do {
            for (int i = 0; i < 10; i++)
                sb.append(_availableIdCharacters[_rnd.nextInt(_availableIdCharacters.length)]);
            result = sb.toString();
        } while (_usedIds.contains(result));

        return result;
    }
}
