package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GenericContextObjects;

import java.util.HashMap;
import java.util.Map;

public class DecisionHolderManager implements DecisionHolder {
    private Map<String, AwaitingDecision> _decisionMap = new HashMap<String, AwaitingDecision>();
    private static final String TYPE = "awaitingDecision";

    @Override
    public AwaitingDecision getDecision(String player) {
        return _decisionMap.get(player);
    }

    @Override
    public void setDecision(String player, AwaitingDecision decision) {
        _decisionMap.put(player, decision);
    }

    @Override
    public AwaitingDecision removeDecision(String player) {
        return _decisionMap.remove(player);
    }

    @Override
    public boolean hasDecision(String player) {
        return _decisionMap.containsKey(player);
    }

    @Override
    public boolean hasDecisions() {
        return _decisionMap.size() > 0;
    }
}
