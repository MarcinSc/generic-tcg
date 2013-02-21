package com.gempukku.tcg.solforge.pass;

import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionPossibility;

public class PassActionPossibility implements GameActionPossibility {
    @Override
    public GameAction createGameAction() {
        return new PassAction();
    }

    @Override
    public String getText() {
        return "Pass";
    }

    @Override
    public String getAttachedObjectId() {
        return "pass";
    }
}
