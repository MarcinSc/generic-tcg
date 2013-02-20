package com.gempukku.tcg.generic.object;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Zone {
    private Collection<GameObject> _gameObjects = new LinkedHashSet<GameObject>();

    public void addObject(GameObject gameObject) {
        _gameObjects.add(gameObject);
    }

    public void removeObject(GameObject gameObject) {
        _gameObjects.remove(gameObject);
    }

    public Collection<GameObject> getGameObjects() {
        return _gameObjects;
    }
}
