package com.gempukku.tcg;

import com.gempukku.tcg.digital.DigitalEnvironment;

public interface GameBuilder {
    public GameObjects getGameObjects();

    public GameProcessor getGameProcessor();

    public DigitalEnvironment getDigitalEnvironment();
}
