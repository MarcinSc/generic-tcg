package com.gempukku.tcg;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Map<String, Object> _gameObjects = new HashMap<String, Object>();
    private Map<String, Map<String, Object>> _playerObjects = new HashMap<String, Map<String, Object>>();

    public void setPlayerObject(String playerName, String name, Object object) {
        ensurePlayerExists(playerName);
        _playerObjects.get(playerName).put(name, object);
    }

    private void ensurePlayerExists(String playerName) {
        if (!_playerObjects.containsKey(playerName))
            _playerObjects.put(playerName, new HashMap<String, Object>());
    }

    public Object getPlayerObject(String playerName, String name) {
        ensurePlayerExists(playerName);
        return _playerObjects.get(playerName).get(name);
    }

    public void setGameObject(String name, Object object) {
        _gameObjects.put(name, object);
    }

    public Object getGameObject(String name) {
        return _gameObjects.get(name);
    }

    
}
