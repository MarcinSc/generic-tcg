package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.event.type.PlayerTurnStartEvent;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import static com.gempukku.tcg.solforge.SolforgeObjects.*;

public class SolforgeRootGameAction implements GameAction {
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
            System.out.println("Player turn");
    }

    private void processSetupTurn(GameState gameState) {
        setNextTurnPhase(gameState, "startOfTurn");
    }

    private void processTriggerStartOfTurn(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_EVENT_ENGINE)
                .emitGameEvent(new PlayerTurnStartEvent(activePlayer));
        setNextTurnPhase(gameState, "afterTriggeringStartOfTurn");
    }

    private void processStartOfTurn(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        SolforgeObjects.extractGameObject(gameState, GAME_OBJECT_MANAGER)
                .visitGameObjects(
                        new GameObjectVisitor() {
                            @Override
                            public boolean visitGameObject(Zone zone, GameObject gameObject) {
                                if (zone.getName().equals("play") && gameObject.getProperty("type").equals("creature")
                                        && gameObject.getProperty("owner").equals(activePlayer)) {
                                    gameObject.setProperty("offensive", "true");
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
