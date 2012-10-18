package com.gempukku.tcg;

import org.w3c.dom.Document;

public interface XmlGame {
    public boolean hasAwaitingDecisions();
    public String getAwaitingDecision(String player);
    public void playerDecided(String player, String answer) throws InvalidDecisionException;
    public void startGameState(String user);
    public Document updateGameState(String user);
}
