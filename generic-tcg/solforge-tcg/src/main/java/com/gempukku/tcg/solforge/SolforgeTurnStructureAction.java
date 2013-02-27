package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.event.type.PlayerTurnStartEvent;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.other.Counter;

import static com.gempukku.tcg.solforge.SolforgeObjects.*;

public class SolforgeTurnStructureAction implements GameAction {
    @Override
    public boolean hasNextGameEffect(GameState gameState) {
        return true;
    }

    @Override
    public void processNextGameEffect(GameState gameState) {
        final String turnPhase = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.TURN_PHASE).getValue();
        if (turnPhase.equals("beforeStartOfTurn"))
            processSetupTurn(gameState);
        else if (turnPhase.equals("startOfTurn"))
            processStartOfTurn(gameState);
        else if (turnPhase.equals("afterGoingOffensive"))
            processTriggerStartOfTurn(gameState);
        else if (turnPhase.equals("afterTriggeringStartOfTurn"))
            processMainPhase(gameState);
        else if (turnPhase.equals("mainPhase"))
            endMainPhase(gameState);
    }

    private void processMainPhase(GameState gameState) {
        setNextTurnPhase(gameState, "mainPhase");
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_ACTION_STACK)
                .stackGameAction(new SolforgeTimingAction());
    }

    private void endMainPhase(GameState gameState) {
        System.out.println("End of main phase");
    }

    private void processSetupTurn(GameState gameState) {
        final Counter turnCounter = SolforgeObjects.extractGameObject(gameState, TURN_COUNTER);
        turnCounter.setValue(turnCounter.getValue() + 1);

        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        final Counter playerTurnCounter = SolforgeObjects.extractPlayerObject(gameState, PLAYER_TURN_COUNTER, activePlayer);
        playerTurnCounter.setValue(playerTurnCounter.getValue() + 1);

        setNextTurnPhase(gameState, "startOfTurn");
    }

    private void processTriggerStartOfTurn(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_EVENT_ENGINE)
                .emitGameEvent(gameState, new PlayerTurnStartEvent(activePlayer));
        setNextTurnPhase(gameState, "afterTriggeringStartOfTurn");
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_ACTION_STACK)
                .stackGameAction(new SolforgeTimingAction());
    }

    private void processStartOfTurn(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        SolforgeObjects.extractGameObject(gameState, GAME_OBJECT_MANAGER)
                .visitGameObjects(
                        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAY_ZONE),
                        new GameObjectVisitor() {
                            @Override
                            public boolean visitGameObject(Zone zone, GameObject gameObject) {
                                if (gameObject.getProperty(Solforge.Properties.CARD_TYPE).equals("creature")
                                        && gameObject.getProperty(Solforge.Properties.OWNER).equals(activePlayer)) {
                                    gameObject.setProperty(Solforge.Properties.OFFENSIVE, "true");
                                }
                                return false;
                            }
                        });
        setNextTurnPhase(gameState, "afterGoingOffensive");
    }

    private void setNextTurnPhase(GameState gameState, String nextTurnPhase) {
        SolforgeObjects.extractGameObject(gameState, TURN_PHASE).setValue(nextTurnPhase);
    }
}
