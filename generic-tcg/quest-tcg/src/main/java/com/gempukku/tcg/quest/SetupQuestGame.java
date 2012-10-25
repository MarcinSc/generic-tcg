package com.gempukku.tcg.quest;

import com.gempukku.tcg.DecisionCallback;
import com.gempukku.tcg.Deck;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.ActionStack;
import com.gempukku.tcg.generic.Event;
import com.gempukku.tcg.generic.actions.EventAction;
import com.gempukku.tcg.generic.objects.CardContainer;
import com.gempukku.tcg.generic.objects.CardIdManager;
import com.gempukku.tcg.generic.objects.CardZone;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SetupQuestGame implements Event {
    private String _playersLocation;
    private String _deckLocation;

    public void setDeckLocation(String deckLocation) {
        _deckLocation = deckLocation;
    }

    public void setPlayersLocation(String playersLocation) {
        _playersLocation = playersLocation;
    }

    @Override
    public void processEvent(EventAction action, ActionStack actionStack, GameState gameState, DecisionCallback decisionCallback) {
        CardContainer cardContainer = (CardContainer) gameState.getGameObject(QuestConstants.CARD_CONTAINER);
        CardZone play = cardContainer.getSharedCardZone(QuestConstants.PLAY);
        CardIdManager cardIdManager = (CardIdManager) gameState.getGameObject(QuestConstants.CARD_ID_MANAGER);

        Set<String> players = (Set<String>) gameState.getGameObject(_playersLocation);
        for (String player : players) {
            Deck playerDeck = (Deck) gameState.getPlayerObject(player, _deckLocation);

            String questBlueprint = playerDeck.getCards("quest").get(0);
            cardIdManager.addNewCardToZone(play, Collections.singletonMap(QuestConstants.CARD_BLUEPRINT, questBlueprint));

            createCardsFromDeck(cardContainer, cardIdManager, player, playerDeck, QuestConstants.DECK, "deck");
            createCardsFromDeck(cardContainer, cardIdManager, player, playerDeck, QuestConstants.LOCATION_DECK, "locationDeck");
            createCardsFromDeck(cardContainer, cardIdManager, player, playerDeck, QuestConstants.SIDEBOARD, "sideboard");
        }
    }

    private void createCardsFromDeck(CardContainer cardContainer, CardIdManager cardIdManager, String player, Deck playerDeck, String gameZoneName, String deckZoneName) {
        CardZone deck = cardContainer.getPlayerCardZone(player, gameZoneName);
        List<String> cardsInDeck = playerDeck.getCards(deckZoneName);
        for (String cardInDeck : cardsInDeck)
            cardIdManager.addNewCardToZone(deck, Collections.singletonMap(QuestConstants.CARD_BLUEPRINT, cardInDeck));
    }
}
