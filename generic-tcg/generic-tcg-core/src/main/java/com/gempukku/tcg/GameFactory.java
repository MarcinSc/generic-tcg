package com.gempukku.tcg;

import java.util.Map;

public interface GameFactory {
    public GameBuilder createNewGameBuilder(String format, Map<String, GameDeck> playersAndDecks);
}
