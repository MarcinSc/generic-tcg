package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;

import java.util.Map;

public interface GameBuilderFactory {
    public GameBuilder createGameBuilder(Map<String, GameDeck> playersAndDecks);
}
