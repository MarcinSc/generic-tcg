package com.gempukku.tcg.generic.event;

public interface GameEventCondition {
    public boolean matches(GameEvent event);
}
