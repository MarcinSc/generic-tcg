package com.gempukku.tcg.generic.flow;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GameFlow;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.decision.AwaitingDecision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiActionGameFlow implements GameFlow {
    private List<GameAction> _actions;

    public void setActions(List<GameAction> gameActions) {
        _actions = gameActions;
    }

    @Override
    public Map<String, AwaitingDecision> processGameState(GameObjects gameObjects) {
        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);

        String subActionIndex = DigitalObjects.getSimpleFlag(gameObjects, "subActionIndex");
        int index = 0;
        if (subActionIndex != null) {
            index = Integer.parseInt(subActionIndex);
            DigitalObjects.setSimpleFlag(gameObjects, "subActionIndex", String.valueOf(index));
        }

        DigitalObject actionObject = getActionObject(gameObjects, digitalEnvironment);
        final GameAction gameAction = _actions.get(index);
        GameActionContext context = new ObjectGameActionContext(gameObjects, actionObject);
        final Map<String, AwaitingDecision> result = gameAction.processNextGameEffect(gameObjects, context);
        if (!gameAction.hasNextGameEffect(gameObjects, context)) {
            digitalEnvironment.destroyObject(actionObject.getId());
            if (index + 1 < _actions.size()) {
                DigitalObjects.setSimpleFlag(gameObjects, "subActionIndex", String.valueOf(index + 1));
            } else {
                DigitalObjects.removeSimpleFlag(gameObjects, "subActionIndex");
            }
        }
        return result;
    }

    private DigitalObject getActionObject(GameObjects gameObjects, DigitalEnvironment digitalEnvironment) {
        DigitalObject actionObject = DigitalObjects.extractObject(gameObjects, "multiActionObject");
        if (actionObject == null) {
            Map<String, String> attrs = new HashMap<String, String>();
            attrs.put("type", "multiActionObject");
            actionObject = digitalEnvironment.createObject(attrs);
        }
        return actionObject;
    }
}
