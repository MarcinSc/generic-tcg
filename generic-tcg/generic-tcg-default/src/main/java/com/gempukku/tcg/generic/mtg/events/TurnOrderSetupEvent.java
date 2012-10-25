package com.gempukku.tcg.generic.mtg.events;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.actions.EventAction;
import com.gempukku.tcg.generic.decisions.YesNoDecision;
import com.gempukku.tcg.generic.events.ChooseRandomEvent;
import com.gempukku.tcg.generic.events.ExtractGameObjectEvent;
import com.gempukku.tcg.generic.events.SendSystemMessageEvent;
import com.gempukku.tcg.generic.mtg.MtgConstants;
import com.gempukku.tcg.generic.mtg.objects.TurnOrder;

import java.util.*;

public class TurnOrderSetupEvent extends ExtractGameObjectEvent {
    @Override
    protected void objectExtracted(Object object, EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        final Set<String> players = new HashSet<String>((Collection<String>) object);
        action.addEvent(
                new ChooseRandomEvent(players) {
                    @Override
                    protected void valueChosen(final String value, EventAction action, ActionStack actionStack, final GameState gameState, DecisionCallback decisionCallback) {
                        action.addEvent(new SendSystemMessageEvent(MtgConstants.MESSAGE_DISPLAY, "Player " + value + " has won the roll"));
                        decisionCallback.sendDecision(value,
                                new YesNoDecision("Do you wish to go first?") {
                                    @Override
                                    protected void yes() {
                                        List<String> playerOrder = new ArrayList<String>();
                                        playerOrder.add(value);
                                        players.remove(value);
                                        playerOrder.addAll(players);
                                        initializeOrder(playerOrder);
                                    }

                                    @Override
                                    protected void no() {
                                        List<String> playerOrder = new ArrayList<String>();
                                        players.remove(value);
                                        playerOrder.addAll(players);
                                        playerOrder.add(value);
                                        initializeOrder(playerOrder);
                                    }

                                    private void initializeOrder(List<String> playerOrder) {
                                        TurnOrder turnOrder = (TurnOrder) gameState.getGameObject(MtgConstants.TURN_ORDER);
                                        turnOrder.setPlayerTurnOrder(playerOrder);
                                    }
                                });
                    }
                });
    }
}
