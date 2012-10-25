package com.gempukku.tcg.quest;

import com.gempukku.tcg.Deck;
import com.gempukku.tcg.GameLogic;
import com.gempukku.tcg.GameMediator;
import com.gempukku.tcg.GameStateObserver;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StartGameAtTest extends AbstractAtTest {
    @Test
    public void testGameStartup() {
        Map<String, Deck> playerDecks = new HashMap<String, Deck>();
        playerDecks.put("p1", createSampleDeck());
        playerDecks.put("p2", createSampleDeck());
        GameLogic gameLogic = createGame(Collections.<String, String>emptyMap(), playerDecks);
        GameMediator<GameStateObserver> gameMediator = new GameMediator<GameStateObserver>(gameLogic);
        gameMediator.startGame();
    }

    private Deck createSampleDeck() {
        Deck deck = new Deck();
        deck.addCards("quest", Collections.singletonList("1"));
        deck.addCards("deck", Collections.singletonList("2"));
        deck.addCards("locationDeck", Collections.singletonList("3"));
        deck.addCards("sideboard", Collections.singletonList("4"));
        return deck;
    }
}
