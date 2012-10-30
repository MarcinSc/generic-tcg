package com.gempukku.tcg;

import java.util.HashMap;
import java.util.Map;

public class UserFeedback implements DecisionCallback {
    private Map<String, AwaitingDecision> _playerAwaitingDecisions = new HashMap<String, AwaitingDecision>();

    @Override
    public void sendDecision(String player, AwaitingDecision awaitingDecision) {
        _playerAwaitingDecisions.put(player, awaitingDecision);
    }

    public boolean hasAwaitingDecisions() {
        return !_playerAwaitingDecisions.isEmpty();
    }

    public AwaitingDecision getPlayerDecision(String player) {
        return _playerAwaitingDecisions.get(player);
    }

    public AwaitingDecision removePlayerDecision(String player) {
        return _playerAwaitingDecisions.remove(player);
    }

    public Map<String, AwaitingDecision> getDecisions() {
        return new HashMap<String, AwaitingDecision>(_playerAwaitingDecisions);
    }
}
