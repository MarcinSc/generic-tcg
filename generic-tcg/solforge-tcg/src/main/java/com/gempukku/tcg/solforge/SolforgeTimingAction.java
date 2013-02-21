package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.action.GameActionPossibility;
import com.gempukku.tcg.generic.decision.ChooseGameObjectDecision;
import com.gempukku.tcg.generic.decision.ChoosePossibleGameActionDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.Zone;

import java.util.*;

public class SolforgeTimingAction implements GameAction {
    @Override
    public boolean hasNextGameEffect(GameState gameState) {
        final Collection<GameObject> waitingTriggers = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.WAITING_TRIGGERS_ZONE).getGameObjects();
        if (waitingTriggers.size() > 0)
            return true;
        final Collection<GameObject> objectsOnStack = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE).getGameObjects();
        if (objectsOnStack.size() > 0)
            return true;

        final Collection<GameActionPossibility> possibleActions = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_ACTION_MANAGER).getPossibleActions(gameState);
        if (possibleActions.size() > 0)
            return true;

        return false;
    }

    @Override
    public void processNextGameEffect(GameState gameState) {
        final Zone waitingTriggersZone = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.WAITING_TRIGGERS_ZONE);
        final Collection<GameObject> waitingTriggers = waitingTriggersZone.getGameObjects();
        if (waitingTriggers.size() > 0) {
            stackNextWaitingTrigger(waitingTriggers, waitingTriggersZone, gameState);
            return;
        }

        final Zone stackZone = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE);
        final Collection<GameObject> objectsOnStack = stackZone.getGameObjects();
        if (objectsOnStack.size() > 0) {
            processNextObjectOnStack(objectsOnStack, stackZone, gameState);
            return;
        }

        final Collection<GameActionPossibility> possibleActions = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_ACTION_MANAGER).getPossibleActions(gameState);
        if (possibleActions.size() > 0) {
            allowUserToPlayAction(possibleActions, gameState);
            return;
        }

        throw new IllegalStateException("Code should never go here");
    }

    private void allowUserToPlayAction(Collection<GameActionPossibility> possibleActions, final GameState gameState) {
        List<GameActionPossibility> possibleActionsList = new ArrayList<GameActionPossibility>(possibleActions);
        String activePlayer = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_TURN).getValue();
        final DecisionHolder decisionHolder = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.DECISION_HOLDER, activePlayer);
        decisionHolder.setDecision(
                new ChoosePossibleGameActionDecision("Choose action to play", possibleActionsList) {
                    @Override
                    protected void gameActionChosen(GameActionPossibility gameActionPossibility) {
                        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_ACTION_STACK)
                                .stackGameAction(gameActionPossibility.createGameAction(gameState));
                    }
                });
    }

    private void processNextObjectOnStack(Collection<GameObject> objectsOnStack, Zone stackZone, GameState gameState) {
        GameObject stackedObject = getLast(objectsOnStack);
        final String type = stackedObject.getProperty("type");
        if (type.equals("card")) {
            final Map<String, String> copiedProperties = new HashMap<String, String>(stackedObject.getAllProperties());
            copiedProperties.put("type", "token");
            final Zone playZone = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE);
            final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER);
            gameObjectManager.createObjectInZone(playZone, copiedProperties);

            final int level = Integer.parseInt(stackedObject.getProperty("level"));
            if (level < 3)
                stackedObject.setProperty("level", String.valueOf(level + 1));
            final Zone discardZone = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.DISCARD_ZONE, stackedObject.getProperty("owner"));
            gameObjectManager.moveObjectBetweenZones(stackZone, discardZone, stackedObject);
        } else {
            throw new IllegalStateException("Unknown type of object on stack:" + type);
        }
    }

    private GameObject getLast(Collection<GameObject> objectsOnStack) {
        GameObject last = null;
        for (GameObject gameObject : objectsOnStack) {
            last = gameObject;
        }
        return last;
    }

    private void stackNextWaitingTrigger(Collection<GameObject> waitingTriggers, final Zone waitingTriggersZone, final GameState gameState) {
        if (waitingTriggers.size() > 1) {
            String activePlayer = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_TURN).getValue();
            final DecisionHolder decisionHolder = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.DECISION_HOLDER, activePlayer);
            decisionHolder.setDecision(
                    new ChooseGameObjectDecision("Choose next trigger to stack", waitingTriggers) {
                        @Override
                        protected void gameObjectChosen(GameObject gameObject) {
                            moveWaitingTriggerToStack(gameState, waitingTriggersZone, gameObject);
                        }
                    }
            );
        } else {
            moveWaitingTriggerToStack(gameState, waitingTriggersZone, waitingTriggers.iterator().next());
        }
    }

    private void moveWaitingTriggerToStack(GameState gameState, Zone waitingTriggersZone, GameObject gameObject) {
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER)
                .moveObjectBetweenZones(
                        waitingTriggersZone,
                        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE),
                        gameObject);
    }
}
