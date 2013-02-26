package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

/**
 * GameObjectEffect executes a smallest atomic action possible on the GameState and GameObject.
 * It allows multiple executions invocations being done on it. When it returns false from its
 * method execution, the effect serie is considered "done" its job. 
 */
public interface GameObjectEffectSerie {

    public boolean execute(GameState gameState, GameObject gameObject);
}