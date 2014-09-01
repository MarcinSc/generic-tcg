package com.gempukku.tcg.generic.decision;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GenericContextObjects;

import java.util.HashMap;
import java.util.Map;

public class DecisionHolderManager implements DecisionHolder {
    private static final String TYPE = "awaitingDecision";

    @Override
    public void setDecision(GameObjects gameState, String player, AwaitingDecision decision) {
        final DigitalEnvironment digitalEnvironment = getDigitalEnvironment(gameState);

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("type", TYPE);
        attributes.put("owner", player);
        attributes.put("decisionType", decision.getType());
        attributes.putAll(decision.getParameters(gameState));

        digitalEnvironment.createObject(attributes);
    }

    private DigitalEnvironment getDigitalEnvironment(GameObjects gameState) {
        return GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DIGITAL_ENVIRONMENT);
    }

    @Override
    public AwaitingDecision getDecision(GameObjects gameState, String player) {
        final DigitalObject digitalObject = DigitalObjects.extractPlayerObject(gameState, TYPE, player);
        return convertToAwaitingDecision(digitalObject);
    }

    @Override
    public AwaitingDecision removeDecision(GameObjects gameState, String player) {
        final DigitalEnvironment digitalEnvironment = getDigitalEnvironment(gameState);

        final DigitalObject digitalObject = DigitalObjects.extractPlayerObject(gameState, TYPE, player);
        digitalEnvironment.destroyObject(digitalObject.getId());

        return convertToAwaitingDecision(digitalObject);
    }

    @Override
    public boolean hasDecision(GameObjects gameState, String player) {
        return DigitalObjects.extractPlayerObject(gameState, TYPE, player) != null;
    }

    @Override
    public boolean hasDecisions(GameObjects gameState) {
        return DigitalObjects.extractFirstObject(gameState, TYPE) != null;
    }

    private AwaitingDecision convertToAwaitingDecision(DigitalObject digitalObject) {
        return null;
    }
}
