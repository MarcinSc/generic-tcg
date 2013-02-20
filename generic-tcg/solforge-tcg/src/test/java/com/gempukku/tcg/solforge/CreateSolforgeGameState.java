package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameProcessor;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.GameBuilder;
import com.gempukku.tcg.generic.GameFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class CreateSolforgeGameState {
    @Test
    public void createState() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("/spring/solforge-context.xml");
        final GameFactory gameStateFactory = ac.getBean("solforgeGameStateFactory", GameFactory.class);
        Set<String> players = new HashSet<String>();
        players.add("P1");
        players.add("P2");
        final GameBuilder gameBuilder = gameStateFactory.createNewGameBuilder("standard", players);
        final GameState gameState = gameBuilder.getGameState();
        final GameProcessor gameProcessor = gameBuilder.getGameProcessor();
        assertNotNull(gameState);
        assertNotNull(gameProcessor);
    }
}
