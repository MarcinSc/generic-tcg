package com.gempukku.tcg.solforge.pass;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.other.Counter;
import com.gempukku.tcg.solforge.SolforgeObjects;

public class PassAction implements GameAction {
    private boolean _resolved = false;

    @Override
    public boolean hasNextGameEffect(GameState gameState) {
        return !_resolved;
    }

    @Override
    public void processNextGameEffect(GameState gameState) {
        final Counter passCounter = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PASS_COUNTER);
        passCounter.setValue(passCounter.getValue() + 1);
        _resolved = true;
    }
}
