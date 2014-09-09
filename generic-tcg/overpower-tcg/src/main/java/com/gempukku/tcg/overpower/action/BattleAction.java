package com.gempukku.tcg.overpower.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.action.GameActionResolver;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.flow.ObjectGameActionContext;
import com.gempukku.tcg.generic.order.PlayerOrder;
import com.gempukku.tcg.generic.phase.PhaseManager;

import java.util.Map;

public class BattleAction implements GameAction {
    private StringEvaluator _concedingFlag;
    private StringEvaluator _passCount;

    public void setConcedingFlag(StringEvaluator concedingFlag) {
        _concedingFlag = concedingFlag;
    }

    @Override
    public boolean hasNextGameEffect(GameObjects gameObjects, GameActionContext context) {
        String concedingFlag = _concedingFlag.getValue(gameObjects, context);
        String concedingPlayer = DigitalObjects.getSimpleFlag(gameObjects, concedingFlag);
        if (concedingPlayer != null)
            return false;

        String passCountAttribute = _passCount.getValue(gameObjects, context);
        int passCount = getPassCount(context, passCountAttribute);
        if (passCount==2)
            return false;
        return true;
    }

    @Override
    public Map<String, AwaitingDecision> processNextGameEffect(GameObjects gameObjects, GameActionContext context) {
        DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        PlayerOrder playerOrder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_ORDER);

        String player = getPlayer(gameObjects, playerOrder, context);

        String actionId = context.getAttribute("actionId");
        String actionObjectId = context.getAttribute("actionObjectId");
        if (actionId == null) {
            GameAction gameAction = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.GAME_ACTION_RESOLVER).resolveGameAction(actionId);
            DigitalObject actionObject = digitalEnvironment.getObjectById(actionObjectId);
            GameActionContext actionContext = new ObjectGameActionContext(gameObjects, actionObject);
            if (gameAction.hasNextGameEffect(gameObjects, actionContext)) {
                gameAction.processNextGameEffect(gameObjects, actionContext);
            } else {
                digitalEnvironment.destroyObject(actionObject.getId());
                context.removeAttribute("actionId");
                context.removeAttribute("actionObjectId");
                
                context.setAttribute("player", playerOrder.getNextPlayerAfter(gameObjects, player));
            }
        } else {
            // Give player a choice
        }

        return null;
    }

    private String getPlayer(GameObjects gameObjects, PlayerOrder playerOrder, GameActionContext context) {
        String player = context.getAttribute("player");
        if (player == null)
            return playerOrder.getFirstPlayer(gameObjects);

        return player;
    }

    private int getPassCount(GameActionContext context, String passCountAttribute) {
        String attribute = context.getAttribute(passCountAttribute);
        if (attribute == null)
            return 0;
        return Integer.parseInt(attribute);
    }
}
