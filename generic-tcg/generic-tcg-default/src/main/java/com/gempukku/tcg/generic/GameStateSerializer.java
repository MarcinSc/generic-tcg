package com.gempukku.tcg.generic;

import org.w3c.dom.Document;

public interface GameStateSerializer<T> {
    public Document serialize(T gameState);
}
