package com.gempukku.tcg;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Map<String, Object> _gameObjects = new HashMap<String, Object>();

    public void setGameObjects(Map<String, Object> gameObjects) {
        _gameObjects.putAll(gameObjects);
    }

    public Collection<Object> getAllGameObjects() {
        return Collections.unmodifiableCollection(_gameObjects.values());
    }

    public void addGameObject(String key, Object value) {
        _gameObjects.put(key, value);
    }

    public Object getGameObject(String name) {
        final Object result = _gameObjects.get(name);
        if (result == null)
            throw new IllegalArgumentException("Object of name " + name + " not found");
        return result;
    }
}
