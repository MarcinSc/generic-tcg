package com.gempukku.tcg.generic.object;

import com.gempukku.tcg.generic.PerPlayerObjectImpl;

public class PerPlayerZone extends PerPlayerObjectImpl<Zone> {
    private String _name;

    public void setName(String name) {
        _name = name;
    }

    @Override
    protected Zone createInitialObject(String player) {
        Zone zone = new Zone();
        zone.setName(_name);
        return zone;
    }
}
