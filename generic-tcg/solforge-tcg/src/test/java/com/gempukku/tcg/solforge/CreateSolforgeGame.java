package com.gempukku.tcg.solforge;

import com.gempukku.tcg.*;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CreateSolforgeGame {
    @Test
    public void createGame() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("/spring/solforge-context.xml");
        final GameFactory gameStateFactory = ac.getBean("solforgeGameStateFactory", GameFactory.class);
        Map<String, GameDeck> playerDecks = new HashMap<String, GameDeck>();
        playerDecks.put("P1", new DefaultGameDeck());
        playerDecks.put("P2", new DefaultGameDeck());
        final GameBuilder gameBuilder = gameStateFactory.createNewGameBuilder("standard", playerDecks);
        final GameState gameState = gameBuilder.getGameState();
        final GameProcessor gameProcessor = gameBuilder.getGameProcessor();
        assertNotNull(gameState);
        assertNotNull(gameProcessor);
    }
}
