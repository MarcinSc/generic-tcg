package com.gempukku.tcg.solforge;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.GameStateFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertNotNull;

public class CreateSolforgeGameState {
    @Test
    public void createState() {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("/spring/solforge-context.xml");
        final GameStateFactory gameStateFactory = ac.getBean("solforgeGameStateFactory", GameStateFactory.class);
        Set<String> players = new HashSet<String>();
        players.add("P1");
        players.add("P2");
        final GameState gameState = gameStateFactory.createNewGameState("standard", players);
        assertNotNull(gameState);
    }
}
