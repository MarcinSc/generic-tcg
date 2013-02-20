package com.gempukku.tcg.generic.object;

import com.gempukku.tcg.PerPlayerObject;

import java.util.HashMap;
import java.util.Map;

public class PerPlayerZone implements PerPlayerObject {
    private Map<String, Zone> _playerZones = new HashMap<String, Zone>();

    @Override
    public Object getObject(String player) {
        Zone zone = _playerZones.get(player);
        if (zone == null) {
            zone = new Zone();
            _playerZones.put(player, zone);
        }
        return zone;
    }
}
