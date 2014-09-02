package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.Map;

/**
 * GameObjectEffect executes a smallest atomic action possible on the GameState and GameObject.
 * It allows multiple executions invocations being done on it. When it returns false from its
 * method execution, the effect serie is considered "done" its job. 
 */
public interface GameObjectEffectSerie {
    public Result execute(GameObjects gameObjects, GameActionContext context);

    public class Result {
        public final boolean _shouldContinue;
        public final Map<String, AwaitingDecision> _decisions;

        public Result(Map<String, AwaitingDecision> decisions, boolean shouldContinue) {
            _decisions = decisions;
            _shouldContinue = shouldContinue;
        }
    }
}
