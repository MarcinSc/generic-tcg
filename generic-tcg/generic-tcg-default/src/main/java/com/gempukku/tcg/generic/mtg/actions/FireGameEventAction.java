package com.gempukku.tcg.generic.mtg.actions;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.actions.OneTimeAction;
import com.gempukku.tcg.generic.mtg.MtgConstants;
import com.gempukku.tcg.generic.mtg.objects.TriggerManager;

import java.util.Map;

public class FireGameEventAction extends OneTimeAction {
    private String _type;
    private Map<String, String> _parameters;

    public FireGameEventAction(String type, Map<String, String> parameters) {
        _type = type;
        _parameters = parameters;
    }

    @Override
    protected void processAction(ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        TriggerManager triggerManager = (TriggerManager) gameState.getGameObject(MtgConstants.TRIGGER_MANAGER);
        triggerManager.distributeGameEvent(gameState, _type, _parameters);
    }
}
