package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.digital.DigitalEnvironment;

import java.util.Map;
import java.util.Set;

public interface GameBuilderFactory {
    public GameBuilder startNewGame(Map<String, GameDeck> playersAndDecks);
    public GameBuilder continueLoadedGame(DigitalEnvironment digitalEnvironment, Set<String> players);
}
