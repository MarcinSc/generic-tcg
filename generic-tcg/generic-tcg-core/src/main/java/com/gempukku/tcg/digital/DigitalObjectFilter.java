package com.gempukku.tcg.digital;

import com.gempukku.tcg.GameObjects;

public interface DigitalObjectFilter {
    public boolean accept(GameObjects gameObjects, DigitalObject context, DigitalObject object);
}
