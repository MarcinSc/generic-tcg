package com.gempukku.tcg.solforge.pass;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.modifier.ActionModifier;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.Collection;
import java.util.Collections;

import static com.gempukku.tcg.solforge.SolforgeObjects.TURN_PHASE;

public class PassActionDefinition implements ActionModifier {
    @Override
    public Collection<GameActionPossibility> getPossibleActions(GameState gameState) {
        if (SolforgeObjects.extractGameObject(gameState, TURN_PHASE).getValue().equals("mainPhase")) {
            // Nothing is on stack, there is no waiting triggers, has battled, and has not passed since last action
            if (SolforgeObjects.extractGameObject(gameState, SolforgeObjects.WAITING_TRIGGERS_ZONE).getGameObjects().size() == 0
                    && SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE).getGameObjects().size() == 0
                    && getPassCount(gameState) < 1
                    && hasBattled(gameState)) {
                final GameActionPossibility pass = new PassActionPossibility();
                return Collections.singleton(pass);
            }
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

    private int getPassCount(GameState gameState) {
        return SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PASS_COUNTER).getValue();
    }
}
