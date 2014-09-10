package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.generic.effect.GameEffect;

public interface GameEffectResolver {
    public GameEffect resolveGameEffect(String id);
}
