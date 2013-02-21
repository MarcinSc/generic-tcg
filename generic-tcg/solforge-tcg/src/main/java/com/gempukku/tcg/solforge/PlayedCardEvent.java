package com.gempukku.tcg.solforge;

import com.gempukku.tcg.generic.event.GameEvent;

public class PlayedCardEvent extends GameEvent {
    public static final String TYPE = "playedCard";

    public PlayedCardEvent() {
        super(TYPE);
    }
}
