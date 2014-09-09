package com.gempukku.tcg.overpower;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.DigitalObjects;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.SpringGameBuilderFactory;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.ChooseDigitalObjectDecision;
import com.gempukku.tcg.generic.decision.ChooseNumberDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.YesNoDecision;
import com.gempukku.tcg.generic.deck.DefaultGameDeck;
import com.gempukku.tcg.generic.filter.PredicateFilter;
import com.gempukku.tcg.generic.order.PlayerOrder;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.google.common.base.Predicate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class OverpowerGameTest {
    public static SpringGameBuilderFactory _gameBuilderFactory;

    public static final String P1 = "p1";
    public static final String P2 = "p2";
    private GameProcessor _gameProcessor;
    private GameObjects _gameObjects;
    private DigitalEnvironment _digitalEnvironment;
    private DecisionHolder _decisionHolder;

    @BeforeClass
    public static void setupFactory() {
        _gameBuilderFactory = new SpringGameBuilderFactory();
        _gameBuilderFactory.setContextPath("classpath:/spring/overpower-tcg-game-context.xml");
        _gameBuilderFactory.setGameProcessorBeanName("gameProcessor");
        _gameBuilderFactory.setGameObjectsFactoryBeanName("gameState");
        _gameBuilderFactory.setDecisionHolderFactoryBeanName("decisionHolder");
        _gameBuilderFactory.initialize();
    }

    @Test
    public void setupGameTest() {
        startSimpleGame();

        final PlayerDigitalObjectStackManager inPlayZone = OverpowerContextObjects.extractGameObject(_gameObjects, OverpowerContextObjects.IN_PLAY_ZONE);

        assertNotNull(_decisionHolder.getDecision(P1));
        assertNotNull(_decisionHolder.getDecision(P2));

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");

        assertNull(_decisionHolder.getDecision(P1));
        assertNotNull(_decisionHolder.getDecision(P2));

        _gameProcessor.playerSentDecision(_gameObjects, P2, "1,2,3");

        List<DigitalObject> p1FrontLine = inPlayZone.getDigitalObjectsInStack(_gameObjects, P1);
        assertEquals(4, p1FrontLine.size());
        assertEquals("c10", getBlueprint(p1FrontLine.get(0)));
        assertEquals("FrontLine", p1FrontLine.get(0).getAttributes().get("position"));
        assertEquals("c24", getBlueprint(p1FrontLine.get(1)));
        assertEquals("FrontLine", p1FrontLine.get(1).getAttributes().get("position"));
        assertEquals("c29", getBlueprint(p1FrontLine.get(2)));
        assertEquals("FrontLine", p1FrontLine.get(2).getAttributes().get("position"));
        assertEquals("c47", getBlueprint(p1FrontLine.get(3)));
        assertEquals("Reserve", p1FrontLine.get(3).getAttributes().get("position"));

        final List<DigitalObject> p2FrontLine = inPlayZone.getDigitalObjectsInStack(_gameObjects, P2);
        assertEquals(4, p2FrontLine.size());
        assertEquals("c24", getBlueprint(p2FrontLine.get(0)));
        assertEquals("FrontLine", p2FrontLine.get(0).getAttributes().get("position"));
        assertEquals("c29", getBlueprint(p2FrontLine.get(1)));
        assertEquals("FrontLine", p2FrontLine.get(1).getAttributes().get("position"));
        assertEquals("c47", getBlueprint(p2FrontLine.get(2)));
        assertEquals("FrontLine", p2FrontLine.get(2).getAttributes().get("position"));
        assertEquals("c10", getBlueprint(p2FrontLine.get(3)));
        assertEquals("Reserve", p2FrontLine.get(3).getAttributes().get("position"));

        assertEquals("DrawAndDiscard", GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PHASE_MANAGER).getPhase(_gameObjects));
    }

    @Test
    public void saveAndRestore() {
        Map<String, GameDeck> decks = new HashMap<String, GameDeck>();
        DefaultGameDeck deck = createDeck();
        decks.put(P1, deck);
        decks.put(P2, deck);

        {
            startNewGame(decks);

            assertNotNull(_decisionHolder.getDecision(P1));
            assertNotNull(_decisionHolder.getDecision(P2));

            _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");

            assertNull(_decisionHolder.getDecision(P1));
            assertNotNull(_decisionHolder.getDecision(P2));
        }

        {
            loadGame(_digitalEnvironment, decks.keySet());

            PlayerDigitalObjectStackManager inPlayZone = OverpowerContextObjects.extractGameObject(_gameObjects, OverpowerContextObjects.IN_PLAY_ZONE);

            _gameProcessor.playerSentDecision(_gameObjects, P2, "1,2,3");

            List<DigitalObject> p1FrontLine = inPlayZone.getDigitalObjectsInStack(_gameObjects, P1);
            assertEquals(4, p1FrontLine.size());
            assertEquals("c10", getBlueprint(p1FrontLine.get(0)));
            assertEquals("FrontLine", p1FrontLine.get(0).getAttributes().get("position"));
            assertEquals("c24", getBlueprint(p1FrontLine.get(1)));
            assertEquals("FrontLine", p1FrontLine.get(1).getAttributes().get("position"));
            assertEquals("c29", getBlueprint(p1FrontLine.get(2)));
            assertEquals("FrontLine", p1FrontLine.get(2).getAttributes().get("position"));
            assertEquals("c47", getBlueprint(p1FrontLine.get(3)));
            assertEquals("Reserve", p1FrontLine.get(3).getAttributes().get("position"));

            final List<DigitalObject> p2FrontLine = inPlayZone.getDigitalObjectsInStack(_gameObjects, P2);
            assertEquals(4, p2FrontLine.size());
            assertEquals("c24", getBlueprint(p2FrontLine.get(0)));
            assertEquals("FrontLine", p2FrontLine.get(0).getAttributes().get("position"));
            assertEquals("c29", getBlueprint(p2FrontLine.get(1)));
            assertEquals("FrontLine", p2FrontLine.get(1).getAttributes().get("position"));
            assertEquals("c47", getBlueprint(p2FrontLine.get(2)));
            assertEquals("FrontLine", p2FrontLine.get(2).getAttributes().get("position"));
            assertEquals("c10", getBlueprint(p2FrontLine.get(3)));
            assertEquals("Reserve", p2FrontLine.get(3).getAttributes().get("position"));
        }

        assertEquals("DrawAndDiscard", GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PHASE_MANAGER).getPhase(_gameObjects));
    }

    @Test
    public void drawAndDiscardPhaseWithDuplicateOnHand() {
        startSimpleGame(Collections.singletonMap("deck", Arrays.asList("p1", "p2", "p3", "p4", "p5", "p6", "p7", "p1")));

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        final PlayerDigitalObjectStackManager handZone = OverpowerContextObjects.extractGameObject(_gameObjects, OverpowerContextObjects.HAND_ZONE);
        List<DigitalObject> handP1 = handZone.getDigitalObjectsInStack(_gameObjects, P1);
        List<DigitalObject> handP2 = handZone.getDigitalObjectsInStack(_gameObjects, P2);
        assertEquals(8, handP1.size());
        assertEquals(8, handP2.size());

        validateDiscardDuplicateDecision(_decisionHolder.getDecision(P1));
        validateDiscardDuplicateDecision(_decisionHolder.getDecision(P2));

        _gameProcessor.playerSentDecision(_gameObjects, P1, ((ChooseDigitalObjectDecision) _decisionHolder.getDecision(P1)).getObjects().get(0).getId());
        _gameProcessor.playerSentDecision(_gameObjects, P2, ((ChooseDigitalObjectDecision) _decisionHolder.getDecision(P2)).getObjects().get(0).getId());

        handP1 = handZone.getDigitalObjectsInStack(_gameObjects, P1);
        assertEquals(7, handP1.size());

        handP2 = handZone.getDigitalObjectsInStack(_gameObjects, P2);
        assertEquals(7, handP2.size());

        final PlayerDigitalObjectStackManager powerPackZone = OverpowerContextObjects.extractGameObject(_gameObjects, OverpowerContextObjects.POWER_PACK_ZONE);
        assertEquals(1, powerPackZone.getDigitalObjectsInStack(_gameObjects, P1).size());
        assertEquals(1, powerPackZone.getDigitalObjectsInStack(_gameObjects, P2).size());

        assertEquals("DrawAndDiscard", GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PHASE_MANAGER).getPhase(_gameObjects));
    }

    private void validateDiscardDuplicateDecision(AwaitingDecision decision) {
        assertTrue(decision instanceof ChooseDigitalObjectDecision);
        ChooseDigitalObjectDecision chooseDuplicateToDiscard = (ChooseDigitalObjectDecision) decision;
        assertEquals(1, chooseDuplicateToDiscard.getMin());
        assertEquals(1, chooseDuplicateToDiscard.getMax());
        assertEquals(2, chooseDuplicateToDiscard.getObjects().size());
        assertEquals("p1", getBlueprint(chooseDuplicateToDiscard.getObjects().get(0)));
        assertEquals("p1", getBlueprint(chooseDuplicateToDiscard.getObjects().get(1)));
    }

    @Test
    public void drawAndDiscardPhaseDiscardUnusable() {
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        final PlayerDigitalObjectStackManager handZone = OverpowerContextObjects.extractGameObject(_gameObjects, OverpowerContextObjects.HAND_ZONE);
        List<DigitalObject> handP1 = handZone.getDigitalObjectsInStack(_gameObjects, P1);
        List<DigitalObject> handP2 = handZone.getDigitalObjectsInStack(_gameObjects, P2);
        assertEquals(8, handP1.size());
        assertEquals(8, handP2.size());

        validateDiscardUnusableDecision(_decisionHolder.getDecision(P1));
        validateDiscardUnusableDecision(_decisionHolder.getDecision(P2));

        _gameProcessor.playerSentDecision(_gameObjects, P1, ((ChooseDigitalObjectDecision) _decisionHolder.getDecision(P1)).getObjects().get(0).getId());
        _gameProcessor.playerSentDecision(_gameObjects, P2, ((ChooseDigitalObjectDecision) _decisionHolder.getDecision(P2)).getObjects().get(0).getId());

        handP1 = handZone.getDigitalObjectsInStack(_gameObjects, P1);
        assertEquals(7, handP1.size());

        handP2 = handZone.getDigitalObjectsInStack(_gameObjects, P2);
        assertEquals(7, handP2.size());

        final PlayerDigitalObjectStackManager deadPileZone = OverpowerContextObjects.extractGameObject(_gameObjects, OverpowerContextObjects.DEAD_PILE_ZONE);
        assertEquals(1, deadPileZone.getDigitalObjectsInStack(_gameObjects, P1).size());
        assertEquals(1, deadPileZone.getDigitalObjectsInStack(_gameObjects, P2).size());

        assertEquals("Placing", GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PHASE_MANAGER).getPhase(_gameObjects));
    }

    @Test
    public void placingPhase() {
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        _gameProcessor.playerSentDecision(_gameObjects, P1, "");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "");

        String firstPlayer = getFirstPlayer();
        String secondPlayer = getNextPlayer(firstPlayer);

        ChooseDigitalObjectDecision firstPlaceCardDecision = (ChooseDigitalObjectDecision) _decisionHolder.getDecision(firstPlayer);

        assertNotNull(firstPlaceCardDecision);
        validatePlaceDecision(firstPlaceCardDecision, 6);
        assertNull(_decisionHolder.getDecision(secondPlayer));

        List<DigitalObject> energyOne = findCardWithBlueprintId(firstPlaceCardDecision.getObjects(), "p1");

        // Choose to place power card with Energy=1
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, energyOne.get(0).getId());

        ChooseDigitalObjectDecision firstPlaceOnCardDecision = (ChooseDigitalObjectDecision) _decisionHolder.getDecision(firstPlayer);

        assertNotNull(firstPlaceOnCardDecision);
        validatePlaceOnDecision(firstPlaceOnCardDecision, 4);

        List<DigitalObject> apocalypse = findCardWithBlueprintId(firstPlaceOnCardDecision.getObjects(), "c10");

        // Choose to place on Apocalypse
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, apocalypse.get(0).getId());

        assertNull(_decisionHolder.getDecision(firstPlayer));
        ChooseDigitalObjectDecision secondPlaceCardDecision = (ChooseDigitalObjectDecision) _decisionHolder.getDecision(secondPlayer);
        validatePlaceDecision(secondPlaceCardDecision, 6);

        // Place chooses to pass
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        firstPlaceCardDecision = (ChooseDigitalObjectDecision) _decisionHolder.getDecision(firstPlayer);
        assertNotNull(firstPlaceCardDecision);
        validatePlaceDecision(firstPlaceCardDecision, 5);
        assertNull(_decisionHolder.getDecision(secondPlayer));

        List<DigitalObject> energySix = findCardWithBlueprintId(firstPlaceCardDecision.getObjects(), "p6");

        // Choose to place power card with Energy=6
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, energySix.get(0).getId());

        firstPlaceOnCardDecision = (ChooseDigitalObjectDecision) _decisionHolder.getDecision(firstPlayer);

        assertNotNull(firstPlaceOnCardDecision);
        validatePlaceOnDecision(firstPlaceOnCardDecision, 1);

        // Choose to place on the only available character without power card and energy>=6 (Cable)
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, firstPlaceOnCardDecision.getObjects().get(0).getId());

        // Second player already passed
        firstPlaceCardDecision = (ChooseDigitalObjectDecision) _decisionHolder.getDecision(firstPlayer);
        assertNotNull(firstPlaceCardDecision);
        validatePlaceDecision(firstPlaceCardDecision, 4);
        assertNull(_decisionHolder.getDecision(secondPlayer));

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "");

        assertEquals("Venture", GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PHASE_MANAGER).getPhase(_gameObjects));
    }

    @Test
    public void venturePhaseValid() {
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        _gameProcessor.playerSentDecision(_gameObjects, P1, "");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "");

        String firstPlayer = getFirstPlayer();
        String secondPlayer = getNextPlayer(firstPlayer);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        ChooseNumberDecision ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "0");

        ChooseNumberDecision ventureReserve = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureReserve.getMin());
        assertEquals(7, ventureReserve.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "1");

        assertEquals("0", DigitalObjects.getSimpleFlag(_gameObjects, "ventureCompleted."+firstPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureReserve."+firstPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureTotal."+firstPlayer));

        ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(secondPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "0");

        ventureReserve = (ChooseNumberDecision) _decisionHolder.getDecision(secondPlayer);
        assertEquals(0, ventureReserve.getMin());
        assertEquals(7, ventureReserve.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "1");

        assertEquals("0", DigitalObjects.getSimpleFlag(_gameObjects, "ventureCompleted."+secondPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureReserve."+secondPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureTotal."+secondPlayer));
    }

    @Test
    public void venturePhaseValidWithDraw() {
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        _gameProcessor.playerSentDecision(_gameObjects, P1, "");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "");

        String firstPlayer = getFirstPlayer();
        String secondPlayer = getNextPlayer(firstPlayer);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        ChooseNumberDecision ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "0");

        ChooseNumberDecision ventureReserve = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureReserve.getMin());
        assertEquals(7, ventureReserve.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "3");

        assertEquals("0", DigitalObjects.getSimpleFlag(_gameObjects, "ventureCompleted."+firstPlayer));
        assertEquals("3", DigitalObjects.getSimpleFlag(_gameObjects, "ventureReserve."+firstPlayer));
        assertEquals("3", DigitalObjects.getSimpleFlag(_gameObjects, "ventureTotal."+firstPlayer));

        YesNoDecision drawCardsDecision = (YesNoDecision) _decisionHolder.getDecision(secondPlayer);
        assertNotNull(drawCardsDecision);

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "yes");

        ChooseDigitalObjectDecision discardUnusable = (ChooseDigitalObjectDecision) _decisionHolder.getDecision(secondPlayer);
        assertEquals(0, discardUnusable.getMin());
        assertEquals(8, discardUnusable.getMax());
        assertEquals(8, discardUnusable.getObjects().size());

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(secondPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "0");

        ventureReserve = (ChooseNumberDecision) _decisionHolder.getDecision(secondPlayer);
        assertEquals(0, ventureReserve.getMin());
        assertEquals(7, ventureReserve.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "1");

        assertEquals("0", DigitalObjects.getSimpleFlag(_gameObjects, "ventureCompleted."+secondPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureReserve."+secondPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureTotal."+secondPlayer));
    }

    @Test
    public void venturePhaseValidWithDrawNoDrawing() {
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        _gameProcessor.playerSentDecision(_gameObjects, P1, "");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "");

        String firstPlayer = getFirstPlayer();
        String secondPlayer = getNextPlayer(firstPlayer);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        ChooseNumberDecision ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "0");

        ChooseNumberDecision ventureReserve = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureReserve.getMin());
        assertEquals(7, ventureReserve.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "3");

        assertEquals("0", DigitalObjects.getSimpleFlag(_gameObjects, "ventureCompleted."+firstPlayer));
        assertEquals("3", DigitalObjects.getSimpleFlag(_gameObjects, "ventureReserve."+firstPlayer));
        assertEquals("3", DigitalObjects.getSimpleFlag(_gameObjects, "ventureTotal."+firstPlayer));

        YesNoDecision drawCardsDecision = (YesNoDecision) _decisionHolder.getDecision(secondPlayer);
        assertNotNull(drawCardsDecision);

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "no");

        ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(secondPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "0");

        ventureReserve = (ChooseNumberDecision) _decisionHolder.getDecision(secondPlayer);
        assertEquals(0, ventureReserve.getMin());
        assertEquals(7, ventureReserve.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "1");

        assertEquals("0", DigitalObjects.getSimpleFlag(_gameObjects, "ventureCompleted."+secondPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureReserve."+secondPlayer));
        assertEquals("1", DigitalObjects.getSimpleFlag(_gameObjects, "ventureTotal."+secondPlayer));
    }

    @Test
    public void venturePhaseInvalid() {
        long start = System.currentTimeMillis();
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        _gameProcessor.playerSentDecision(_gameObjects, P1, "");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "");

        String firstPlayer = getFirstPlayer();
        String secondPlayer = getNextPlayer(firstPlayer);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        ChooseNumberDecision ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "0");

        ChooseNumberDecision ventureReserve = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureReserve.getMin());
        assertEquals(7, ventureReserve.getMax());

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "0");

        ventureCompleted = (ChooseNumberDecision) _decisionHolder.getDecision(firstPlayer);
        assertEquals(0, ventureCompleted.getMin());
        assertEquals(0, ventureCompleted.getMax());

        System.out.println("Time: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void venturePhaseAskForConcede() {
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        _gameProcessor.playerSentDecision(_gameObjects, P1, "");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "");

        String firstPlayer = getFirstPlayer();
        String secondPlayer = getNextPlayer(firstPlayer);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        // venture 0+1
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "0");
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "1");

        // venture 0+1
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "0");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "1");

        YesNoDecision concedeDecision = (YesNoDecision) _decisionHolder.getDecision(firstPlayer);
        assertNotNull(concedeDecision);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "no");

        concedeDecision = (YesNoDecision) _decisionHolder.getDecision(secondPlayer);
        assertNotNull(concedeDecision);

        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "no");

        assertEquals("Battle", GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PHASE_MANAGER).getPhase(_gameObjects));
    }

    @Test
    public void venturePhaseConcede() {
        startSimpleGame();

        _gameProcessor.playerSentDecision(_gameObjects, P1, "0,1,2");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "0,1,2");

        _gameProcessor.playerSentDecision(_gameObjects, P1, "");
        _gameProcessor.playerSentDecision(_gameObjects, P2, "");

        String firstPlayer = getFirstPlayer();
        String secondPlayer = getNextPlayer(firstPlayer);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "");

        // venture 0+1
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "0");
        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "1");

        // venture 0+1
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "0");
        _gameProcessor.playerSentDecision(_gameObjects, secondPlayer, "1");

        YesNoDecision concedeDecision = (YesNoDecision) _decisionHolder.getDecision(firstPlayer);
        assertNotNull(concedeDecision);

        _gameProcessor.playerSentDecision(_gameObjects, firstPlayer, "yes");

        assertEquals(firstPlayer, DigitalObjects.getSimpleFlag(_gameObjects, "concedingPlayer"));

        assertEquals("Battle", GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PHASE_MANAGER).getPhase(_gameObjects));
    }

    private List<DigitalObject> findCardWithBlueprintId(List<DigitalObject> cards, final String blueprintId) {
        return DigitalObjectUtils.filter(_gameObjects, new PredicateFilter(
                new Predicate<DigitalObject>() {
                    @Override
                    public boolean apply(DigitalObject input) {
                        return blueprintId.equals(input.getAttributes().get("blueprintId"));
                    }
                }), null, cards);
    }

    private void validatePlaceOnDecision(ChooseDigitalObjectDecision decision, int characterCount) {
        assertEquals(1, decision.getMin());
        assertEquals(1, decision.getMax());
        assertEquals(characterCount, decision.getObjects().size());
    }

    private void validatePlaceDecision(ChooseDigitalObjectDecision decision, int cardCount) {
        assertEquals(0, decision.getMin());
        assertEquals(1, decision.getMax());
        assertEquals(cardCount, decision.getObjects().size());
    }

    private String getNextPlayer(String currentPlayer) {
        PlayerOrder playerOrder = GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PLAYER_ORDER);
        return playerOrder.getNextPlayerAfter(_gameObjects, currentPlayer);
    }

    private String getFirstPlayer() {
        PlayerOrder playerOrder = GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.PLAYER_ORDER);
        return playerOrder.getFirstPlayer(_gameObjects);
    }

    private void validateDiscardUnusableDecision(AwaitingDecision decision) {
        assertTrue(decision instanceof ChooseDigitalObjectDecision);
        ChooseDigitalObjectDecision chooseUnusableDecisions = (ChooseDigitalObjectDecision) decision;
        assertEquals(0, chooseUnusableDecisions.getMin());
        assertEquals(8, chooseUnusableDecisions.getMax());
        assertEquals(8, chooseUnusableDecisions.getObjects().size());
    }

    private String getBlueprint(DigitalObject digitalObject) {
        return digitalObject.getAttributes().get("blueprintId");
    }

    private void startSimpleGame() {
        startSimpleGame(Collections.<String, List<String>>emptyMap());
    }

    private void startSimpleGame(Map<String, List<String>> deckOverlay) {
        Map<String, GameDeck> decks = new HashMap<String, GameDeck>();
        DefaultGameDeck deck = createDeck();
        for (Map.Entry<String, List<String>> overlay: deckOverlay.entrySet()){
            deck.addDeckPart(overlay.getKey(), overlay.getValue());
        }

        decks.put(P1, deck);
        decks.put(P2, deck);

        startNewGame(decks);
    }

    private void startNewGame(Map<String, GameDeck> decks) {
        GameBuilder gameBuilder = _gameBuilderFactory.startNewGame(decks);

        initFromGameBuilder(gameBuilder);
    }

    private void loadGame(DigitalEnvironment digitalEnvironment, Set<String> players) {
        GameBuilder gameBuilder = _gameBuilderFactory.continueLoadedGame(digitalEnvironment, players);

        initFromGameBuilder(gameBuilder);
    }

    private void initFromGameBuilder(GameBuilder gameBuilder) {
        _gameProcessor = gameBuilder.getGameProcessor();
        _gameObjects = gameBuilder.getGameObjects();
        _digitalEnvironment = gameBuilder.getDigitalEnvironment();
        _decisionHolder = GenericContextObjects.extractGameObject(_gameObjects, GenericContextObjects.DECISION_HOLDER);
    }

    private DefaultGameDeck createDeck() {
        DefaultGameDeck deck = new DefaultGameDeck();
        deck.addDeckPart("characters", Arrays.asList("c10", "c24", "c29", "c47"));
        deck.addDeckPart("deck", Arrays.asList("p1", "p2", "p3", "p4", "p5", "p6", "p7", "p8"));
        deck.addDeckPart("mission", Arrays.asList("m1", "m2", "m3", "m4", "m5", "m6", "m7"));
        return deck;
    }
}
