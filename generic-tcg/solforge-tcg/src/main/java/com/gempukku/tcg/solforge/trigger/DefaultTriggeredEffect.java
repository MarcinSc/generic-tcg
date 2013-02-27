package com.gempukku.tcg.solforge.trigger;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameObjectEventCondition;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.HashMap;
import java.util.Map;

public class DefaultTriggeredEffect implements TriggeredEffect {
    private GameObjectEventCondition _gameObjectEventCondition;
    private String _triggerId;

    public void setTriggerId(String triggerId) {
        _triggerId = triggerId;
    }

    public void setGameObjectEventCondition(GameObjectEventCondition gameObjectEventCondition) {
        _gameObjectEventCondition = gameObjectEventCondition;
    }

    @Override
    public void processPossibleEvent(GameState gameState, GameObject triggerFrom, GameEvent event) {
        if (_gameObjectEventCondition.matches(gameState, triggerFrom, event)) {
            final Zone waitingTriggersZone = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.WAITING_TRIGGERS_ZONE);
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("type", "trigger");
            properties.put("owner", triggerFrom.getProperty("owner"));
            properties.put("triggerId", _triggerId);
            properties.put("sourceId", triggerFrom.getIdentifier());
            SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER)
                    .createObjectInZone(waitingTriggersZone, properties);
        }
    }
}
