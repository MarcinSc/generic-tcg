package com.gempukku.tcg.solforge.battle;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.modifier.ActionModifier;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.Collection;
import java.util.Collections;

public class BattleActionDefinition implements ActionModifier {
    @Override
    public Collection<GameActionPossibility> getPossibleActions(GameState gameState) {
        // Nothing is on stack, there is no waiting triggers and has not battled this turn yet
        if (SolforgeObjects.extractGameObject(gameState, SolforgeObjects.WAITING_TRIGGERS_ZONE).getGameObjects().size() == 0
                && SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE).getGameObjects().size() == 0
                && !hasBattled(gameState)) {
            final GameActionPossibility battle = new BattleActionPossibility();
            return Collections.singleton(battle);
        }
        return Collections.emptySet();
    }

    private boolean hasBattled(GameState gameState) {
        for (GameEvent gameEvent : SolforgeObjects.extractGameObject(gameState, SolforgeObjects.TURN_EVENT_COLLECTOR).getGameEvents()) {
            if (gameEvent.getType().equals("battled"))
                return true;
        }
        return false;
    }
}
