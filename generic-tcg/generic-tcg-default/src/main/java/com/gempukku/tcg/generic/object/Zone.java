package com.gempukku.tcg.generic.object;

import java.util.LinkedHashSet;
import java.util.Set;

public class Zone {
    private Set<GameObject> _gameObjects = new LinkedHashSet<GameObject>();

    public void addObject(GameObject gameObject) {
        _gameObjects.add(gameObject);
    }

    public void removeObject(GameObject gameObject) {
        _gameObjects.remove(gameObject);
    }

    public Set<GameObject> getGameObjects() {
        return _gameObjects;
    }
}
