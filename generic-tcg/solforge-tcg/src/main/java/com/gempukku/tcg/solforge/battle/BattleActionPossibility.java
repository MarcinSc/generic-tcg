package com.gempukku.tcg.solforge.battle;

import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionPossibility;

public class BattleActionPossibility implements GameActionPossibility {
    @Override
    public GameAction createGameAction() {
        return new BattleAction();
    }

    @Override
    public String getText() {
        return "Battle!";
    }

    @Override
    public String getAttachedObjectId() {
        return "battle";
    }
}
