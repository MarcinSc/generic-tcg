package com.gempukku.tcg.overpower;

import com.gempukku.tcg.DefaultGameDeck;
import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.SpringGameBuilderFactory;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;
import org.junit.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class OverpowerGameTest {

    public static final String P1 = "p1";
    public static final String P2 = "p2";

    @Test
    public void startupTest() {
        SpringGameBuilderFactory gameBuilderFactory = new SpringGameBuilderFactory();
        gameBuilderFactory.setContextPath("classpath:/spring/overpower-tcg-game-context.xml");
        gameBuilderFactory.setGameProcessorBeanName("gameProcessor");
        gameBuilderFactory.setGameStateBeanName("gameState");

        Map<String, GameDeck> decks = new HashMap<String, GameDeck>();
        DefaultGameDeck deck = new DefaultGameDeck();
        deck.addDeckPart("characters", Arrays.asList("1", "2", "3", "4"));
        deck.addDeckPart("deck", Arrays.asList("5"));
        decks.put(P1, deck);
        decks.put(P2, deck);

        final GameBuilder gameBuilder = gameBuilderFactory.createGameBuilder(decks);

        final GameProcessor gameProcessor = gameBuilder.getGameProcessor();
        final GameObjects gameObjects = gameBuilder.getGameObjects();

        final PlayerDigitalObjectStackManager frontLineZone = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.FRONT_LINE_ZONE);

        DecisionHolder decisionHolder = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DECISION_HOLDER);
        assertNotNull(decisionHolder.getDecision(P1));
        assertNotNull(decisionHolder.getDecision(P2));

        gameProcessor.playerSentDecision(gameObjects, P1, "0,1,2");

        List<DigitalObject> p1FrontLine = frontLineZone.getDigitalObjectsInStack(gameObjects, P1);
        assertEquals(0, p1FrontLine.size());

        assertNull(decisionHolder.getDecision(P1));
        assertNotNull(decisionHolder.getDecision(P2));

        gameProcessor.playerSentDecision(gameObjects, P2, "1,2,3");
        assertNull(decisionHolder.getDecision(P1));
        assertNull(decisionHolder.getDecision(P2));

        p1FrontLine = frontLineZone.getDigitalObjectsInStack(gameObjects, P1);
        assertEquals(3, p1FrontLine.size());
        assertEquals("1", p1FrontLine.get(0).getAttributes().get("blueprintId"));
        assertEquals("2", p1FrontLine.get(1).getAttributes().get("blueprintId"));
        assertEquals("3", p1FrontLine.get(2).getAttributes().get("blueprintId"));

        final List<DigitalObject> p2FrontLine = frontLineZone.getDigitalObjectsInStack(gameObjects, P2);
        assertEquals(3, p2FrontLine.size());
        assertEquals("2", p2FrontLine.get(0).getAttributes().get("blueprintId"));
        assertEquals("3", p2FrontLine.get(1).getAttributes().get("blueprintId"));
        assertEquals("4", p2FrontLine.get(2).getAttributes().get("blueprintId"));

        assertNotNull(decisionHolder.getDecision("end"));
    }
}
