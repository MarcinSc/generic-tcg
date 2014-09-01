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
    public void setDecision(GameObjects gameObjects, String player, AwaitingDecision decision) {
        final DigitalEnvironment digitalEnvironment = getDigitalEnvironment(gameObjects);

        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("type", TYPE);
        attributes.put("owner", player);

        SerializeDecisionVisitor visitor = new SerializeDecisionVisitor(gameObjects);
        decision.accept(visitor);
        attributes.putAll(visitor.getResult());

        digitalEnvironment.createObject(attributes);
    }

    private DigitalEnvironment getDigitalEnvironment(GameObjects gameState) {
        return GenericContextObjects.extractGameObject(gameState, GenericContextObjects.DIGITAL_ENVIRONMENT);
    }

    @Override
    public AwaitingDecision getDecision(GameObjects gameObjects, String player) {
        final DigitalObject digitalObject = DigitalObjects.extractPlayerObject(gameObjects, TYPE, player);
        return convertToAwaitingDecision(digitalObject);
    }

    @Override
    public AwaitingDecision removeDecision(GameObjects gameObjects, String player) {
        final DigitalEnvironment digitalEnvironment = getDigitalEnvironment(gameObjects);

        final DigitalObject digitalObject = DigitalObjects.extractPlayerObject(gameObjects, TYPE, player);
        digitalEnvironment.destroyObject(digitalObject.getId());

        return convertToAwaitingDecision(digitalObject);
    }

    @Override
    public boolean hasDecision(GameObjects gameObjects, String player) {
        return DigitalObjects.extractPlayerObject(gameObjects, TYPE, player) != null;
    }

    @Override
    public boolean hasDecisions(GameObjects gameObjects) {
        return DigitalObjects.extractObjects(gameObjects, TYPE) != null;
    }

    private AwaitingDecision convertToAwaitingDecision(DigitalObject digitalObject) {
        return null;
    }
}
