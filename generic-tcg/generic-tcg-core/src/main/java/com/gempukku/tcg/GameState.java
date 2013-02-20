package com.gempukku.tcg;

import java.util.Map;

public class GameState {
    private Map<String, Object> _gameObjects;

    public void setGameObjects(Map<String, Object> gameObjects) {
        _gameObjects = gameObjects;
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
