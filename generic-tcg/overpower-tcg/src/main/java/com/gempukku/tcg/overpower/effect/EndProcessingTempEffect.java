package com.gempukku.tcg.overpower.effect;

import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.decision.YesNoDecision;
import com.gempukku.tcg.generic.effect.GameObjectEffectSerie;

import java.util.HashMap;
import java.util.Map;

public class EndProcessingTempEffect implements GameObjectEffectSerie {
    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        Map<String, AwaitingDecision> decision = new HashMap<String, AwaitingDecision>();
        decision.put("end", new YesNoDecision("Finished!") {
            
        });
        return new Result(decision, false);
    }
}
