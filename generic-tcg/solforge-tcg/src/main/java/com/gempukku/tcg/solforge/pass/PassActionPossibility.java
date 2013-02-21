package com.gempukku.tcg.solforge.pass;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionPossibility;

public class PassActionPossibility implements GameActionPossibility {
    @Override
    public GameAction createGameAction(GameState gameState) {
        return new PassAction();
    }

    @Override
    public String getText(GameState gameState) {
        return "Pass";
    }

    @Override
    public String getAttachedObjectId(GameState gameState) {
        return "pass";
    }
}
