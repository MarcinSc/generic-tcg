package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.filter.GameObjectFilter;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.util.GameObjectUtils;

import java.util.Set;

public class DestroyGameObjectEffect extends GameObjectEffect {
    private String _gameObjectManager;
    private String _zone;
    private String _playerZone;
    private GameObjectFilter _gameObjectFilter;

    public void setGameObjectManager(String gameObjectManager) {
        _gameObjectManager = gameObjectManager;
    }

    public void setZone(String zone) {
        _zone = zone;
    }

    public void setPlayerZone(String playerZone) {
        _playerZone = playerZone;
    }

    public void setGameObjectFilter(GameObjectFilter gameObjectFilter) {
        _gameObjectFilter = gameObjectFilter;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObject) {
        final Zone zone = GameObjectUtils.resolveZone(gameState, gameObject, _zone, _playerZone);

        final GameObjectManager gameObjectManager = (GameObjectManager) gameState.getGameObject(_gameObjectManager);
        if (_gameObjectFilter == null) {
            gameObjectManager.destroyObjectInZone(zone, gameObject);
        } else {
            final Set<GameObject> objectsMatching = gameObjectManager.findObjectsMatching(zone, gameState, _gameObjectFilter);
            for (GameObject object : objectsMatching)
                gameObjectManager.destroyObjectInZone(zone, object);
        }
    }
}
