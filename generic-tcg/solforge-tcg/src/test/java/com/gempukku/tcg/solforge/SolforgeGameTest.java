package com.gempukku.tcg.solforge;

import com.gempukku.tcg.*;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.object.Zone;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

import static org.junit.Assert.*;

public class SolforgeGameTest {

    public static final String P1 = "P1";
    public static final String P2 = "P2";

    private GameProcessor _gameProcessor;
    private PerPlayerObject<DecisionHolder> _decisionHolder;
    private GameState _gameState;
    private Zone _playZone;

    @Test
    public void testTurnSetup() throws InvalidAnswerException {
        final DefaultGameDeck p1Deck = createDefaultDeck();
        final DefaultGameDeck p2Deck = createDefaultDeck();

        setupGameWithDecks(p1Deck, p2Deck);

        assertEquals(1, SolforgeObjects.extractGameObject(_gameState, SolforgeObjects.TURN_COUNTER).getValue());
        assertEquals(1, SolforgeObjects.extractPlayerObject(_gameState, SolforgeObjects.PLAYER_TURN_COUNTER, P1).getValue());
        assertEquals(0, SolforgeObjects.extractPlayerObject(_gameState, SolforgeObjects.PLAYER_TURN_COUNTER, P2).getValue());

        assertEquals(5, SolforgeObjects.extractPlayerObject(_gameState, SolforgeObjects.HAND_ZONE, P1).getGameObjects().size());
        assertEquals(5, SolforgeObjects.extractPlayerObject(_gameState, SolforgeObjects.HAND_ZONE, P2).getGameObjects().size());
    }

    @Test
    public void testPlayingCreature() throws InvalidAnswerException {
        final DefaultGameDeck p1Deck = createDefaultDeck();
        final DefaultGameDeck p2Deck = createDefaultDeck();

        setupGameWithDecks(p1Deck, p2Deck);

        AwaitingDecision decision = _decisionHolder.getObject(P1).getDecision();
        assertEquals("CHOOSE_POSSIBLE_ACTION", decision.getType());
        assertTrue(decision.getParameters(_gameState).get("0").endsWith("Play Air Spirit"));

        // Play Air Spirit
        _gameProcessor.playerSentDecision(_gameState, P1, "0");

        decision = _decisionHolder.getObject(P1).getDecision();
        assertEquals("CHOOSE_OBJECT", decision.getType());
        assertTrue(decision.getParameters(_gameState).get("ids").equals("lane:1,lane:2,lane:3,lane:4,lane:5"));

        _gameProcessor.playerSentDecision(_gameState, P1, "lane:3");

        final Collection<GameObject> inDiscard = SolforgeObjects.extractPlayerObject(_gameState, SolforgeObjects.DISCARD_ZONE, P1).getGameObjects();
        assertEquals(1, inDiscard.size());
        assertEquals("2", inDiscard.iterator().next().getProperty("level"));

        final Collection<GameObject> inPlay = _playZone.getGameObjects();
        assertEquals(1, inPlay.size());
        final GameObject airSpirit = inPlay.iterator().next();
        assertEquals("token", airSpirit.getProperty("type"));
        assertEquals(P1, airSpirit.getProperty("owner"));
        assertEquals("card_1", airSpirit.getProperty("blueprintId"));
        assertEquals("lane:3", airSpirit.getProperty("lane"));
        assertEquals("0", airSpirit.getProperty("damage"));
        assertEquals("1", airSpirit.getProperty("level"));
    }

    @Test
    public void testTriggeredEffects() throws InvalidAnswerException {
        final DefaultGameDeck p1Deck = createDefaultDeck();
        final DefaultGameDeck p2Deck = createDefaultDeck();

        setupGameWithDecks(p1Deck, p2Deck);

        final String owner = P1;
        final String blueprintId = "card_2";
        int lane = 3;
        int level = 2;

        putCreatureIntoPlay("id", owner, blueprintId, level, lane);

        // Play Air Spirit to lane 3
        _gameProcessor.playerSentDecision(_gameState, P1, "0");
        _gameProcessor.playerSentDecision(_gameState, P1, "lane:3");

        AwaitingDecision decision = _decisionHolder.getObject(P1).getDecision();
        final String possibleIds = decision.getParameters(_gameState).get("ids");
        assertTrue(possibleIds.contains("object:id"));
        assertTrue(possibleIds.contains("player:"+P1));
        assertTrue(possibleIds.contains("player:"+P2));

        _gameProcessor.playerSentDecision(_gameState, P1, "player:"+P2);

        assertEquals(97, SolforgeObjects.extractPlayerObject(_gameState, SolforgeObjects.HEALTH_COUNTER, P2).getValue());
    }

    private void putCreatureIntoPlay(String id, String owner, String blueprintId, int level, int lane) {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("owner", owner);
        properties.put("level", String.valueOf(level));
        properties.put("blueprintId", blueprintId);
        properties.put("cardType", "creature");
        properties.put("type", "token");
        properties.put("lane", "lane:"+lane);
        properties.put("offensive", "false");
        properties.put("damage", "0");
        GameObject gameObject = new GameObject();
        gameObject.setIdentifier(id);
        gameObject.getAllProperties().putAll(properties);
        _playZone.addObject(gameObject);
    }

    private void setupGameWithDecks(DefaultGameDeck p1Deck, DefaultGameDeck p2Deck) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("/spring/solforge-context.xml");
        final GameFactory gameStateFactory = ac.getBean("solforgeGameStateFactory", GameFactory.class);
        Map<String, GameDeck> playerDecks = new HashMap<String, GameDeck>();
        playerDecks.put(P1, p1Deck);
        playerDecks.put(P2, p2Deck);
        final GameBuilder gameBuilder = gameStateFactory.createNewGameBuilder("standard", playerDecks);
        _gameState = gameBuilder.getGameState();
        _gameProcessor = gameBuilder.getGameProcessor();
        assertNotNull(_gameState);
        assertNotNull(_gameProcessor);

        _decisionHolder = SolforgeObjects.extractGameObject(_gameState, SolforgeObjects.DECISION_HOLDER);
        int decisionCount = 0;
        for (DecisionHolder holder : _decisionHolder.getAllObjects()) {
            if (holder.getDecision() != null)
                decisionCount++;
        }
        assertEquals(1, decisionCount);

        // P1 decides to start, or P2 decides to go second
        if (_decisionHolder.getObject(P1).getDecision() != null)
            _gameProcessor.playerSentDecision(_gameState, P1, "yes");
        else
            _gameProcessor.playerSentDecision(_gameState, P2, "no");

        _playZone = SolforgeObjects.extractGameObject(_gameState, SolforgeObjects.PLAY_ZONE);
    }

    private DefaultGameDeck createDefaultDeck() {
        final DefaultGameDeck defaultGameDeck = new DefaultGameDeck();
        List<String> cardsInMainDeck = new LinkedList<String>();
        cardsInMainDeck.add("card_1");
        cardsInMainDeck.add("card_1");
        cardsInMainDeck.add("card_1");
        cardsInMainDeck.add("card_1");
        cardsInMainDeck.add("card_1");
        defaultGameDeck.addDeckPart("main", cardsInMainDeck);
        return defaultGameDeck;
    }
}
