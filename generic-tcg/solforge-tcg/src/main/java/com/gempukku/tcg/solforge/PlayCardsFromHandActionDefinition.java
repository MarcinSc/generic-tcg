package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.action.GameAction;
import com.gempukku.tcg.generic.event.GameEvent;
import com.gempukku.tcg.generic.modifier.ActionModifier;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PlayCardsFromHandActionDefinition implements ActionModifier {
    @Override
    public Collection<GameAction> getPossibleActions(GameState gameState) {
        String activePlayer = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_TURN).getValue();
        // Nothing is on stack and there is no waiting triggers
        if (SolforgeObjects.extractGameObject(gameState, SolforgeObjects.WAITING_TRIGGERS_ZONE).getGameObjects().size() == 0
                && SolforgeObjects.extractGameObject(gameState, SolforgeObjects.STACK_ZONE).getGameObjects().size() == 0) {
            int playedCards = countPlayedCardsThisTurn(gameState);
            // Maximum number of played cards is 2 (can be modifier, to add later)
            if (playedCards < 2) {
                List<GameAction> possibleActions = new LinkedList<GameAction>();

                final SolforgeCardBlueprintResolver solforgeCardBlueprintResolver = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER);
                Collection<GameObject> cardsInHand = SolforgeObjects.extractPlayerObject(gameState, SolforgeObjects.HAND_ZONE, activePlayer).getGameObjects();
                for (GameObject gameObject : cardsInHand) {
                    final String blueprintId = gameObject.getProperty("blueprintId");
                    final SolforgeCardBlueprint cardBlueprint = solforgeCardBlueprintResolver.getCardBlueprint(blueprintId);
                    final Collection<String> playCardActionIds = cardBlueprint.getPlayCardActionIds();
                    for (String playCardActionId : playCardActionIds) {
                        final GameAction gameAction = solforgeCardBlueprintResolver.getCardActionBlueprint(playCardActionId).getGameAction(
                                gameState, gameObject);
                        if (gameAction != null)
                            possibleActions.add(gameAction);
                    }

                }
                return possibleActions;
            }
        }
        return Collections.emptySet();
    }

    private int countPlayedCardsThisTurn(GameState gameState) {
        int playedCards = 0;
        for (GameEvent gameEvent : SolforgeObjects.extractGameObject(gameState, SolforgeObjects.TURN_EVENT_COLLECTOR).getGameEvents()) {
            if (gameEvent.getType().equals(PlayedCardEvent.TYPE))
                playedCards++;
        }
        return playedCards;
    }
}
