package com.gempukku.tcg.generic.decision;

import java.util.HashMap;
import java.util.Map;

public class DecisionHolderManager implements DecisionHolder {
    private Map<String, AwaitingDecision> _decisionMap = new HashMap<String, AwaitingDecision>();

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
