package com.gempukku.tcg;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Map<String, Object> _gameObjects = new HashMap<String, Object>();
    private Map<String, Map<String, Object>> _playerObjects = new HashMap<String, Map<String, Object>>();

    public void addPlayer(String name) {
        _playerObjects.put(name, new HashMap<String, Object>());
    }

    public void setPlayerObject(String playerName, String name, Object object) {
        _playerObjects.get(playerName).put(name, object);
    }

    public Object getPlayerObject(String playerName, String name) {
        return _playerObjects.get(playerName).get(name);
    }

    public void setGameObject(String name, Object object) {
        _gameObjects.put(name, object);
    }

    public Object getGameObject(String name) {
        return _gameObjects.get(name);
    }

    
}
