package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;

public class SolforgeRootGameAction implements GameAction {
    @Override
    public boolean hasNextGameEffect(GameState gameState) {
        return true;
    }

    @Override
    public void processNextGameEffect(GameState gameState) {
        final String turnPhase = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.TURN_PHASE).getValue();
        if (turnPhase.equals("startOfTurn"))
        // TODO
    }
}
