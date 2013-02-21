package com.gempukku.tcg.solforge.battle;

import com.gempukku.tcg.generic.event.GameEvent;

public class BattledEvent extends GameEvent {
    public static final String TYPE = "battled";

    public BattledEvent() {
        super(TYPE);
    }
}
