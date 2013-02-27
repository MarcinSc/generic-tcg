package com.gempukku.tcg.solforge.trigger;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.object.GameObject;

public interface TriggeredEffect {
    public void processPossibleEvent(GameState gameState, GameObject triggerFrom, GameEvent event);
}
