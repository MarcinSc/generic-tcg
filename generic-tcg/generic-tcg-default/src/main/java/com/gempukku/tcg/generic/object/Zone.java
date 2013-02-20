package com.gempukku.tcg.generic.object;

import java.util.Collection;
import java.util.LinkedHashSet;

public class Zone {
    private String _name;
    private Collection<GameObject> _gameObjects = new LinkedHashSet<GameObject>();

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

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
