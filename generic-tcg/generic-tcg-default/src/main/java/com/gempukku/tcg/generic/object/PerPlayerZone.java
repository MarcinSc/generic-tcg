package com.gempukku.tcg.generic.object;

import com.gempukku.tcg.generic.PerPlayerObjectImpl;

public class PerPlayerZone extends PerPlayerObjectImpl<Zone> {
    @Override
    protected Zone createInitialObject(String player) {
        return new Zone();
    }
}
