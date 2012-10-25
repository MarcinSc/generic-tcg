package com.gempukku.tcg.quest;

import com.gempukku.tcg.Deck;
import com.gempukku.tcg.GameLogic;
import com.gempukku.tcg.generic.ActionStackSpringGameDefinition;

import java.util.Map;

public class AbstractAtTest {
    protected GameLogic createGame(Map<String, String> parameters, Map<String, Deck> decks) {
        ActionStackSpringGameDefinition gameDefinition = new ActionStackSpringGameDefinition("classpath:/spring/quest-root.xml",
                "questRootAction", "questFinishCondition", "questGameStateObserver");
        gameDefinition.setDecksLocation("deck");
        gameDefinition.setPlayersLocation("players");
        gameDefinition.setGameParametersLocation("gameParameters");
        return gameDefinition.createGameLogic(parameters, decks);
    }
}
