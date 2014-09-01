package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GameFlow;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.card.CardManager;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.ChooseArbitraryCardDecision;
import com.gempukku.tcg.generic.phase.PhaseManager;
import com.gempukku.tcg.generic.player.PlayerManager;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetupPhaseGameFlow implements GameFlow {
    @Override
    public Map<String, AwaitingDecision> processGameState(GameObjects gameObjects) {
        String subPhase = DigitalObjects.getSimpleFlag(gameObjects, "subPhase");
        if (subPhase == null) {
            subPhase = "selectCharacters";
            DigitalObjects.setSimpleFlag(gameObjects, "subPhase", subPhase);
        }

        if (subPhase.equals("selectCharacters")) {
            processSelectCharacters(gameObjects);
        } else if (subPhase.equals("setupDecks")) {
            processSetupDecks(gameObjects);
        }

        return null;
    }

    private Map<String, AwaitingDecision> processSelectCharacters(final GameObjects gameObjects) {
        final CardManager cardManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.CARD_MANAGER);
        final PlayerManager playerManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_MANAGER);
        final PlayerDigitalObjectStackManager setupCharacters = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.SETUP_CHARACTERS_ZONE);

        Map<String, AwaitingDecision> awaitingDecisions = new HashMap<String, AwaitingDecision>();
        for (final String player : playerManager.getPlayerNames(gameObjects)) {
            final String selectedCharacters = DigitalObjects.getSimpleFlag(gameObjects, "selectedCharacters." + player);
            if (selectedCharacters == null) {
                List<String> characterBlueprints = new ArrayList<String>();
                final List<DigitalObject> cardsInStack = setupCharacters.getDigitalObjectsInStack(gameObjects, player);
                for (DigitalObject card : cardsInStack) {
                    characterBlueprints.add(cardManager.getBlueprintId(card));
                }

                awaitingDecisions.put(player,
                        new ChooseArbitraryCardDecision("Choose starting characters", characterBlueprints, 3, 3) {
                            @Override
                            protected void objectsChosen(List<Integer> indices, List<String> blueprintId) {
                                DigitalObjects.setSimpleFlag(gameObjects, "selectedCharacters."+player, StringUtils.join(indices, ","));
                            }
                        });
            }
        }

        if (awaitingDecisions.isEmpty()) {
            final PlayerDigitalObjectStackManager frontLineZone = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.FRONT_LINE_ZONE);
            final PlayerDigitalObjectStackManager reserveZone = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.RESERVE_ZONE);

            // Everyone has already selected their characters
            for (final String player : playerManager.getPlayerNames(gameObjects)) {
                final String[] selectedIndices = DigitalObjects.getSimpleFlag(gameObjects, "selectedCharacters." + player).split(",");
                final List<DigitalObject> cardsInStack = setupCharacters.getDigitalObjectsInStack(gameObjects, player);
                setupCharacters.removeAllObjectsInStack(gameObjects, player);

                for (int i=0; i<4; i++) {
                    if (contains(selectedIndices, i)) {
                        frontLineZone.putOnTop(gameObjects, player, cardsInStack.get(i));
                    } else {
                        reserveZone.putOnTop(gameObjects, player, cardsInStack.get(i));
                    }
                }
            }

            DigitalObjects.setSimpleFlag(gameObjects, "subPhase", "setupDecks");

            return null;
        }

        return awaitingDecisions;
    }

    private boolean contains(String[] selectedIndices, int i) {
        for (String selectedIndex : selectedIndices) {
            if (selectedIndex.equals(String.valueOf(i)))
                return true;
        }
        return false;
    }

    private void processSetupDecks(GameObjects gameObjects) {
        final PlayerManager playerManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PLAYER_MANAGER);
        final PhaseManager phaseManager = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.PHASE_MANAGER);

        final PlayerDigitalObjectStackManager setupDeck = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.SETUP_DECK_ZONE);
        final PlayerDigitalObjectStackManager deck = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.DECK_ZONE);

        for (String player : playerManager.getPlayerNames(gameObjects)) {
            final List<DigitalObject> cardsInStack = setupDeck.getDigitalObjectsInStack(gameObjects, player);
            setupDeck.removeAllObjectsInStack(gameObjects, player);
            for (DigitalObject cardInStack : cardsInStack) {
                deck.putOnTop(gameObjects, player, cardInStack);
            }
            deck.shuffleItemsInStack(gameObjects, player);
        }

        DigitalObjects.removeSimpleFlag(gameObjects, "subPhase");

        phaseManager.setPhase(gameObjects, "Start");
    }
}
