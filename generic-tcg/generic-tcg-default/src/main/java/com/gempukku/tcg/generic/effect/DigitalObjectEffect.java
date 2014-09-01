package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

/**
 * GameObjectEffect executes a smallest atomic action possible on the GameState and GameObject.
 * Used for "one-shot" effects, that do not require multiple invocations.
 */
public abstract class DigitalObjectEffect implements GameObjectEffectSerie {
    @Override
    public final boolean execute(GameObjects gameState, DigitalObject digitalObject) {
        executeGameEffect(gameState, digitalObject);
        return false;
    }

    public abstract void executeGameEffect(GameObjects gameState, DigitalObject digitalObject);
}
