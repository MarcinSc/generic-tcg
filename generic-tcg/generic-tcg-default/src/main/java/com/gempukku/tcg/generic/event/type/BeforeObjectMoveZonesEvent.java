package com.gempukku.tcg.generic.event.type;

import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.Zone;

public class BeforeObjectMoveZonesEvent extends GameEvent {
    private GameObject _gameObject;
    private Zone _zoneFrom;
    private Zone _zoneTo;

    public BeforeObjectMoveZonesEvent(GameObject gameObject, Zone zoneFrom, Zone zoneTo) {
        super(GenericEventType.BEFORE_MOVE_ZONES);
        _gameObject = gameObject;
        _zoneFrom = zoneFrom;
        _zoneTo = zoneTo;
    }

    public GameObject getGameObject() {
        return _gameObject;
    }

    public Zone getZoneFrom() {
        return _zoneFrom;
    }

    public Zone getZoneTo() {
        return _zoneTo;
    }
}
