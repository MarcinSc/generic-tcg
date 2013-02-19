package com.gempukku.tcg;

public interface GameEventCondition {
    public boolean matches(GameEvent event);
}
