package com.gempukku.tcg;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Map<String, Object> _gameObjects = new HashMap<String, Object>();

    public void setGameObjects(Map<String, Object> gameObjects) {
        _gameObjects.putAll(gameObjects);
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

    public Object getPlayerObject(String player, String name) {
        final Object result = _gameObjects.get(name);
        if (result instanceof PerPlayerObject) {
            final Object value = ((PerPlayerObject) result).getObject(player);
            if (value == null)
                throw new IllegalArgumentException("Object of name " + name + " for player " + player + " not found");
            return value;
        }
        throw new IllegalArgumentException("Object of name " + name + " for player " + player + " not found");
    }
}
