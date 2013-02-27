package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.util.GameObjectUtils;

public class RemoveGameObjectEffect extends GameObjectEffect {
    private String _gameObjectManager;
    private String _zone;
    private String _playerZone;

    public void setGameObjectManager(String gameObjectManager) {
        _gameObjectManager = gameObjectManager;
    }

    public void setZone(String zone) {
        _zone = zone;
    }

    public void setPlayerZone(String playerZone) {
        _playerZone = playerZone;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObject) {
        ((GameObjectManager) gameState.getGameObject(_gameObjectManager))
                .destroyObjectInZone(GameObjectUtils.resolveZone(gameState, gameObject, _zone, _playerZone), gameObject);
    }
}
