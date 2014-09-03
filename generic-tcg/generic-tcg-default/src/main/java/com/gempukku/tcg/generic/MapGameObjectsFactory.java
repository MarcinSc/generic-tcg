package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.GameObjectsFactory;

import java.util.HashMap;
import java.util.Map;

public class MapGameObjectsFactory implements GameObjectsFactory {
    private Map<String, Object> _gameObjects = new HashMap<String, Object>();

    public void setGameObjects(Map<String, Object> gameObjects) {
        _gameObjects.putAll(gameObjects);
    }

    @Override
    public GameObjects create() {
        GameObjects gameObjects = new GameObjects();
        gameObjects.setGameObjects(_gameObjects);
        return gameObjects;
    }
}
