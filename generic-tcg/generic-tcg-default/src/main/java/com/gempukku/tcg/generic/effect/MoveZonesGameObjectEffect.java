package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEventEngine;
import com.gempukku.tcg.generic.event.type.AfterObjectMoveZonesEvent;
import com.gempukku.tcg.generic.event.type.BeforeObjectMoveZonesEvent;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.util.GameObjectUtils;

public class MoveZonesGameObjectEffect extends GameObjectEffect {
    private String _zoneFrom;
    private String _playerZoneFrom;
    private String _zoneTo;
    private String _playerZoneTo;
    private String _gameObjectManager;
    private String _gameEventEngine;

    public void setZoneFrom(String zoneFrom) {
        _zoneFrom = zoneFrom;
    }

    public void setPlayerZoneFrom(String playerZoneFrom) {
        _playerZoneFrom = playerZoneFrom;
    }

    public void setZoneTo(String zoneTo) {
        _zoneTo = zoneTo;
    }

    public void setPlayerZoneTo(String playerZoneTo) {
        _playerZoneTo = playerZoneTo;
    }

    public void setGameObjectManager(String gameObjectManager) {
        _gameObjectManager = gameObjectManager;
    }

    public void setGameEventEngine(String gameEventEngine) {
        _gameEventEngine = gameEventEngine;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObjectSource) {
        Zone zoneFrom = getZoneFrom(gameState, gameObjectSource);
        Zone zoneTo = getZoneTo(gameState, gameObjectSource);

        GameEventEngine gameEventEngine = (GameEventEngine) gameState.getGameObject(_gameEventEngine);

        gameEventEngine.emitGameEvent(
                gameState, new BeforeObjectMoveZonesEvent(gameObjectSource, zoneFrom, zoneTo));

        ((GameObjectManager) gameState.getGameObject(_gameObjectManager))
                .moveObjectBetweenZones(zoneFrom, zoneTo, gameObjectSource);

        gameEventEngine.emitGameEvent(
                gameState, new AfterObjectMoveZonesEvent(gameObjectSource, zoneFrom, zoneTo));
    }

    private Zone getZoneFrom(GameState gameState, GameObject gameObject) {
        return GameObjectUtils.resolveZone(gameState, gameObject, _zoneFrom, _playerZoneFrom);
    }

    private Zone getZoneTo(GameState gameState, GameObject gameObjectSource) {
        return GameObjectUtils.resolveZone(gameState, gameObjectSource, _zoneTo, _playerZoneTo);
    }
}
