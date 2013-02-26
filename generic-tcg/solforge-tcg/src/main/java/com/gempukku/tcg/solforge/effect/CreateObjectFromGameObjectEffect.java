package com.gempukku.tcg.solforge.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.GameObjectEffect;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.util.GameObjectUtils;

import java.util.HashMap;
import java.util.Map;

public class CreateObjectFromGameObjectEffect extends GameObjectEffect {
    private String _gameObjectManager;
    private String _zoneTo;
    private String _playerZoneTo;
    private Map<String, String> _extraProperties;

    public void setGameObjectManager(String gameObjectManager) {
        _gameObjectManager = gameObjectManager;
    }

    public void setZoneTo(String zoneTo) {
        _zoneTo = zoneTo;
    }

    public void setPlayerZoneTo(String playerZoneTo) {
        _playerZoneTo = playerZoneTo;
    }

    public void setExtraProperties(Map<String, String> extraProperties) {
        _extraProperties = extraProperties;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObjectSource) {
        Zone zone = GameObjectUtils.resolveZone(gameState, gameObjectSource, _zoneTo, _playerZoneTo);
        Map<String, String> newProperties = new HashMap<String, String>(gameObjectSource.getAllProperties());
        if (_extraProperties != null)
            newProperties.putAll(_extraProperties);
        ((GameObjectManager) gameState.getGameObject(_gameObjectManager))
                .createObjectInZone(zone, newProperties);
    }
}
