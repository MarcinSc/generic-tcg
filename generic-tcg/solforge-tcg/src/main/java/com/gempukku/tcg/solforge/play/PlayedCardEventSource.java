package com.gempukku.tcg.solforge.play;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameObjectEventSource;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.Solforge;

public class PlayedCardEventSource implements GameObjectEventSource {
    @Override
    public GameEvent createGameEvent(GameState gameState, GameObject gameObject) {
        return new PlayedCardEvent(gameObject, gameObject.getProperty(Solforge.Properties.OWNER));
    }
}
