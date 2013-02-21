package com.gempukku.tcg.generic.object;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Zone {
    private String _name;
    private List<GameObject> _gameObjects = new ArrayList<GameObject>();

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void shuffle() {
        Collections.shuffle(_gameObjects);
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
