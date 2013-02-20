package com.gempukku.tcg.generic;

import java.util.Set;

public interface GameBuilderFactory {
    public GameBuilder createGameBuilder(Set<String> players);
}
