package com.gempukku.tcg.generic;

import java.util.Set;

public interface GameFactory {
    public GameBuilder createNewGameBuilder(String format, Set<String> players);
}
