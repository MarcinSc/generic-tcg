package com.gempukku.tcg.solforge.battle;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionPossibility;

public class BattleActionPossibility implements GameActionPossibility {
    @Override
    public GameAction createGameAction(GameState gameState) {
        return new BattleAction();
    }

    @Override
    public String getText(GameState gameState) {
        return "Battle!";
    }

    @Override
    public String getAttachedObjectId(GameState gameState) {
        return "battle";
    }
}
