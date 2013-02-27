package com.gempukku.tcg.solforge;

import com.gempukku.tcg.*;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;
import com.gempukku.tcg.generic.object.GameObject;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

import static org.junit.Assert.*;

public class CreateSolforgeGame {

    public static final String P1 = "P1";
    public static final String P2 = "P2";

    private GameProcessor _gameProcessor;
    private PerPlayerObject<DecisionHolder> _decisionHolder;
    private GameState _gameState;

    @Test
    public void createGame() throws InvalidAnswerException {
        final DefaultGameDeck p1Deck = createDefaultDeck();
        final DefaultGameDeck p2Deck = createDefaultDeck();

        setupGameWithDecks(p1Deck, p2Deck);

        AwaitingDecision decision = _decisionHolder.getObject(P1).getDecision();
        assertEquals("CHOOSE_POSSIBLE_ACTION", decision.getType());
        assertTrue(decision.getParameters(_gameState).get("0").endsWith("Play Air Spirit"));
        assertEquals("battle,Battle!", decision.getParameters(_gameState).get("1"));

        // Play Air Spirit
        _gameProcessor.playerSentDecision(_gameState, P1, "0");

        decision = _decisionHolder.getObject(P1).getDecision();
        assertEquals("CHOOSE_OBJECT", decision.getType());
        assertTrue(decision.getParameters(_gameState).get("ids").equals("lane:1,lane:2,lane:3,lane:4,lane:5"));

        _gameProcessor.playerSentDecision(_gameState, P1, "lane:3");

        final Collection<GameObject> inDiscard = SolforgeObjects.extractPlayerObject(_gameState, SolforgeObjects.DISCARD_ZONE, P1).getGameObjects();
        assertEquals(1, inDiscard.size());
        assertEquals("2", inDiscard.iterator().next().getProperty("level"));

        final Collection<GameObject> inPlay = SolforgeObjects.extractGameObject(_gameState, SolforgeObjects.PLAY_ZONE).getGameObjects();
        assertEquals(1, inPlay.size());
        assertEquals("token", inPlay.iterator().next().getProperty("type"));
        assertEquals(P1, inPlay.iterator().next().getProperty("owner"));
        assertEquals("card_1", inPlay.iterator().next().getProperty("blueprintId"));
        assertEquals("lane:3", inPlay.iterator().next().getProperty("lane"));
        assertEquals("1", inPlay.iterator().next().getProperty("level"));

        decision = _decisionHolder.getObject(P1).getDecision();
        assertEquals("CHOOSE_POSSIBLE_ACTION", decision.getType());
        assertEquals("battle,Battle!", decision.getParameters(_gameState).get("0"));
        assertNull(decision.getParameters(_gameState).get("1"));

        // Go to Battle
        _gameProcessor.playerSentDecision(_gameState, P1, "0");

        decision = _decisionHolder.getObject(P1).getDecision();
        assertEquals("CHOOSE_POSSIBLE_ACTION", decision.getType());
        assertEquals("pass,Pass", decision.getParameters(_gameState).get("0"));

        System.out.println("Finished");
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
    }

    private DefaultGameDeck createDefaultDeck() {
        final DefaultGameDeck defaultGameDeck = new DefaultGameDeck();
        List<String> cardsInMainDeck = new LinkedList<String>();
        cardsInMainDeck.add("card_1");
        defaultGameDeck.addDeckPart("main", cardsInMainDeck);
        return defaultGameDeck;
    }
}
