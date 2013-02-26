package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

/**
 * GameObjectEffect executes a smallest atomic action possible on the GameState and GameObject.
 * Used for "one-shot" effects, that do not require multiple invocations.
 */
public abstract class GameObjectEffect implements GameObjectEffectSerie {
    @Override
    public final boolean execute(GameState gameState, GameObject gameObject) {
        executeGameEffect(gameState, gameObject);
        return false;
    }

    public abstract void executeGameEffect(GameState gameState, GameObject gameObject);
}
