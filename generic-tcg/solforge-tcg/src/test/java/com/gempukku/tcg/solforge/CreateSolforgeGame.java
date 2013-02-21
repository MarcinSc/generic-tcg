package com.gempukku.tcg.solforge;

import com.gempukku.tcg.*;
import com.gempukku.tcg.generic.decision.AwaitingDecision;
import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.generic.decision.InvalidAnswerException;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateSolforgeGame {

    public static final String P1 = "P1";
    public static final String P2 = "P2";

    @Test
    public void createGame() throws InvalidAnswerException {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("/spring/solforge-context.xml");
        final GameFactory gameStateFactory = ac.getBean("solforgeGameStateFactory", GameFactory.class);
        Map<String, GameDeck> playerDecks = new HashMap<String, GameDeck>();
        playerDecks.put(P1, createDefaultDeck());
        playerDecks.put(P2, createDefaultDeck());
        final GameBuilder gameBuilder = gameStateFactory.createNewGameBuilder("standard", playerDecks);
        final GameState gameState = gameBuilder.getGameState();
        final GameProcessor gameProcessor = gameBuilder.getGameProcessor();
        assertNotNull(gameState);
        assertNotNull(gameProcessor);

        final PerPlayerObject<DecisionHolder> decisionHolder = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.DECISION_HOLDER);
        int decisionCount = 0;
        for (DecisionHolder holder : decisionHolder.getAllObjects()) {
            if (holder.getDecision() != null)
                decisionCount++;
        }
        assertEquals(1, decisionCount);

        gameProcessor.playerSentDecision(gameState, P1, "yes");
        gameProcessor.playerSentDecision(gameState, P2, "yes");

        String activePlayer = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.PLAYER_TURN).getValue();
        AwaitingDecision decision = decisionHolder.getObject(activePlayer).getDecision();
        assertEquals("CHOOSE_POSSIBLE_ACTION", decision.getType());
        assertEquals("battle,Battle!", decision.getParameters().get("0"));

        gameProcessor.playerSentDecision(gameState, activePlayer, "0");

        decision = decisionHolder.getObject(activePlayer).getDecision();
        assertEquals("CHOOSE_POSSIBLE_ACTION", decision.getType());
        assertEquals("pass,Pass", decision.getParameters().get("0"));

        System.out.println("Finished");
    }

    private DefaultGameDeck createDefaultDeck() {
        final DefaultGameDeck defaultGameDeck = new DefaultGameDeck();
        List<String> cardsInMainDeck = new LinkedList<String>();
        cardsInMainDeck.add("card_1");
        defaultGameDeck.addDeckPart("main", cardsInMainDeck);
        return defaultGameDeck;
    }
}
