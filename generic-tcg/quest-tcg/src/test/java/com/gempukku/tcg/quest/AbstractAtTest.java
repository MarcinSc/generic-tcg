package com.gempukku.tcg.quest;

import com.gempukku.tcg.*;
import com.gempukku.tcg.generic.ActionStackSpringGameDefinition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AbstractAtTest {
    public static final String P1 = "p1";
    public static final String P2 = "p2";
    private GameLogic _gameLogic;
    private GameState _gameState;
    private UserFeedback _userFeedback;

    private GameLogic createGame(Map<String, String> parameters, Map<String, Deck> decks) {
        ActionStackSpringGameDefinition gameDefinition = new ActionStackSpringGameDefinition("classpath:/spring/quest-root.xml",
                "questRootAction", "questFinishCondition", "questGameStateObserverFactory", "questGameStateEvaluator");
        gameDefinition.setDecksLocation("deck");
        gameDefinition.setPlayersLocation("players");
        gameDefinition.setGameParametersLocation("gameParameters");
        return gameDefinition.createGameLogic(parameters, decks);
    }

    public void setupGame(Map<String, Deck> decks) throws InvalidDecisionException {
        _gameLogic = createGame(Collections.<String, String>emptyMap(), decks);
        _userFeedback = new UserFeedback();
        _gameState = new GameState();

        processGame();

        AwaitingDecision p1Decision = getDecision(P1);
        AwaitingDecision p2Decision = getDecision(P2);
        if (p1Decision != null)
            playerDecided(P1, "yes");
        if (p2Decision != null)
            playerDecided(P2, "no");
    }

    public void setupSampleGame() throws InvalidDecisionException {
        Map<String, Deck> playerDecks = new HashMap<String, Deck>();
        playerDecks.put(P1, createSampleDeck());
        playerDecks.put(P2, createSampleDeck());
        setupGame(playerDecks);
    }

    public void processGame() {
        while (!_userFeedback.hasAwaitingDecisions()
                && !_gameLogic.isFinished(_gameState, _userFeedback)) {
            _gameLogic.proceed(_gameState, _userFeedback);
        }
    }

    public void playerDecided(String player, String answer) throws InvalidDecisionException {
        AwaitingDecision awaitingDecision = _userFeedback.removePlayerDecision(player);
        try {
            awaitingDecision.playerDecided(answer);
        } catch (InvalidDecisionException exp) {
            _userFeedback.sendDecision(player, awaitingDecision);
            throw exp;
        }
    }

    public AwaitingDecision getProcessedDecision(String player) {
        processGame();
        return getDecision(player);
    }

    public AwaitingDecision getDecision(String player) {
        return _userFeedback.getPlayerDecision(player);
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
