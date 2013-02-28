package com.gempukku.tcg.solforge.damage;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.event.GameObjectEventCondition;
import com.gempukku.tcg.generic.object.GameObject;

public class DealDamageToOpponentEventCondition implements GameObjectEventCondition {
    @Override
    public boolean matches(GameState gameState, GameObject conditionOn, GameEvent event) {
        if (event.getType().equals(DamagePlayerEvent.TYPE)) {
            DamagePlayerEvent damagePlayer = (DamagePlayerEvent) event;
            if (damagePlayer.getFrom() == conditionOn)
                return true;
        }
        return false;
    }
}
