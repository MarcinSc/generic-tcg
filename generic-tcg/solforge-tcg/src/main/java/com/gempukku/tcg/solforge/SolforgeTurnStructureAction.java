package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.event.type.PlayerTurnEndEvent;
import com.gempukku.tcg.generic.event.type.PlayerTurnStartEvent;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.GameObjectManager;
import com.gempukku.tcg.generic.object.GameObjectVisitor;
import com.gempukku.tcg.generic.object.Zone;
import com.gempukku.tcg.generic.other.Counter;
import com.gempukku.tcg.generic.other.Property;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            processTriggerEndOfTurn(gameState);
        else if (turnPhase.equals("afterTriggeringEndOfTurn"))
            processDiscardCardsInHand(gameState);
        else if (turnPhase.equals("afterDiscardingHand"))
            levelPlayerIfApplicable(gameState);
        else if (turnPhase.equals("afterLeveling"))
            drawNewHand(gameState);
        else if (turnPhase.equals("afterDrawingNewHand"))
            passToNextPlayer(gameState);
    }

    private void passToNextPlayer(GameState gameState) {
        final Property playerTurn = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN);
        
        final String activePlayer = playerTurn.getValue();
        final String[] playerOrder = SolforgeObjects.extractGameObject(gameState, PLAYER_ORDER).getValue().split(",");
        final String nextPlayer = findNextPlayer(playerOrder, activePlayer);
        playerTurn.setValue(nextPlayer);
        setNextTurnPhase(gameState, "beforeStartOfTurn");
    }

    private String findNextPlayer(String[] playerOrder, String player) {
        for (int i=0; i<playerOrder.length; i++)
            if (playerOrder[i].equals(player)) {
                if (i == playerOrder.length-1)
                    return playerOrder[0];
                else
                    return playerOrder[i+1];
            }
        throw new IllegalArgumentException("Did not find active player in list of players: "+player);
    }

    private void drawNewHand(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        final Zone deck = SolforgeObjects.extractPlayerObject(gameState, DECK_ZONE, activePlayer);
        final Zone hand = SolforgeObjects.extractPlayerObject(gameState, HAND_ZONE, activePlayer);
        final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, GAME_OBJECT_MANAGER);
        final List<GameObject> cardsToDraw = deck.getTopMostObjects(5);
        for (GameObject gameObject : cardsToDraw)
            gameObjectManager.moveObjectBetweenZones(deck, hand, gameObject);
        setNextTurnPhase(gameState, "afterDrawingNewHand");
    }

    private void levelPlayerIfApplicable(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        final int playerTurn = SolforgeObjects.extractPlayerObject(gameState, PLAYER_TURN_COUNTER, activePlayer).getValue();
        if (playerTurn % 4 == 0 && playerTurn < 9) {
            final Counter playerLevel = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.LEVEL_COUNTER, activePlayer);
            playerLevel.setValue(playerLevel.getValue() + 1);
            final Zone discard = SolforgeObjects.extractPlayerObject(gameState, DISCARD_ZONE, activePlayer);
            final Zone deck = SolforgeObjects.extractPlayerObject(gameState, DECK_ZONE, activePlayer);
            Set<GameObject> cardsInDiscard = new HashSet<GameObject>(discard.getGameObjects());
            final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, GAME_OBJECT_MANAGER);
            for (GameObject gameObject : cardsInDiscard)
                gameObjectManager.moveObjectBetweenZones(discard, deck, gameObject);
            deck.shuffle();
        }
        setNextTurnPhase(gameState, "afterLeveling");
    }

    private void processDiscardCardsInHand(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        final GameObjectManager gameObjectManager = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_OBJECT_MANAGER);
        final Zone hand = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.HAND_ZONE, activePlayer);
        final Zone discard = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.DISCARD_ZONE, activePlayer);
        final Collection<GameObject> cardsInHand = new HashSet<GameObject>(hand.getGameObjects());
        for (GameObject gameObject : cardsInHand)
            gameObjectManager.moveObjectBetweenZones(hand, discard, gameObject);
        setNextTurnPhase(gameState, "afterDiscardingHand");
    }

    private void processTriggerEndOfTurn(GameState gameState) {
        final String activePlayer = SolforgeObjects.extractGameObject(gameState, PLAYER_TURN).getValue();
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_EVENT_ENGINE)
                .emitGameEvent(gameState, new PlayerTurnEndEvent(activePlayer));
        setNextTurnPhase(gameState, "afterTriggeringEndOfTurn");
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_ACTION_STACK)
                .stackGameAction(new SolforgeTimingAction());
    }

    private void processMainPhase(GameState gameState) {
        setNextTurnPhase(gameState, "mainPhase");
        SolforgeObjects.extractGameObject(gameState, SolforgeObjects.GAME_ACTION_STACK)
                .stackGameAction(new SolforgeTimingAction());
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
