package com.gempukku.tcg.generic;

public interface GameModifierConsumer<T extends GameModifier> {
    public void registerModifier(T gameModifier);

    public void removeModifier(T gameModifier);
}
