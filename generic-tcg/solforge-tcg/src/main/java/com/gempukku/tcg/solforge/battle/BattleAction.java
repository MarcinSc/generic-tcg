package com.gempukku.tcg.solforge.battle;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.solforge.SolforgeObjects;

public class BattleAction implements GameAction {
    private boolean _resolved = false;

    @Override
    public boolean hasNextGameEffect(GameState gameState) {
        return !_resolved;
    }

    @Override
    public void processNextGameEffect(GameState gameState) {
        System.out.println("Here is the battling done!");
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_EVENT_ENGINE).emitGameEvent(
                new BattledEvent());
        _resolved = true;
    }
}
