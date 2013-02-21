package com.gempukku.tcg.generic.action;

public interface GameActionPossibility {
    public String getText();

    public String getAttachedObjectId();

    public GameAction createGameAction();
}
