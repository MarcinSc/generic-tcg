package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.Map;

/**
 * GameObjectEffect executes a smallest atomic action possible on the GameState and GameObject.
 * It allows multiple executions invocations being done on it. When it returns false from its
 * method execution, the effect serie is considered "done" its job. 
 */
public interface GameEffect {
    public Result execute(GameObjects gameObjects, GameEffectContext context);

    public class Result {
        public final boolean _shouldContinue;
        public final Map<String, AwaitingDecision> _decisions;

        public static Result decisions(Map<String, AwaitingDecision> decisions) {
            return new Result(decisions, true);
        }

        public static Result cont() {
            return new Result(null, true);
        }

        public static Result pass() {
            return new Result(null, false);
        }

        private Result(Map<String, AwaitingDecision> decisions, boolean shouldContinue) {
            if (decisions != null && !shouldContinue)
                throw new IllegalArgumentException("Can't contain decisions and not continue");
            _decisions = decisions;
            _shouldContinue = shouldContinue;
        }
    }
}
