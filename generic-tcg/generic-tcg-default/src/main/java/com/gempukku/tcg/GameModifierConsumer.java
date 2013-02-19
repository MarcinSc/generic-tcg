package com.gempukku.tcg;

public interface GameModifierConsumer<T extends GameModifier> {
    public void registerModifier(T gameModifier);

    public void removeModifier(T gameModifier);
}
