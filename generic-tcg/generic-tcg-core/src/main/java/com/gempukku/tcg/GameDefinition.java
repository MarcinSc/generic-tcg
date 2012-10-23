package com.gempukku.tcg;

import java.util.Map;

public interface GameDefinition {
    public GameLogic createGameLogic(Map<String, String> parameters, Map<String, Deck> playerDecks);
}
