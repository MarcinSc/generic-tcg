package com.gempukku.tcg.quest;

import com.gempukku.tcg.AwaitingDecision;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.GameStateEvaluator;
import com.gempukku.tcg.UserFeedback;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class QuestGameStateEvaluator implements GameStateEvaluator<QuestGameStateObserver> {
    private Set<QuestGameStateObserver> _observers = new HashSet<QuestGameStateObserver>();
    private Map<String, AwaitingDecision> _awaitingDecisionMap;

    @Override
    public void addGameStateObserver(QuestGameStateObserver gameStateObserver) {
        _observers.add(gameStateObserver);
        sendPlayerDecision(gameStateObserver);
    }

    @Override
    public void removeGameStateObserver(QuestGameStateObserver gameStateObserver) {
        _observers.remove(gameStateObserver);
    }

    @Override
    public void gameStateChanged(GameState gameState, UserFeedback userFeedback) {
        _awaitingDecisionMap = userFeedback.getDecisions();
        for (QuestGameStateObserver observer : _observers) {
            sendPlayerDecision(observer);
        }
    }

    private void sendPlayerDecision(QuestGameStateObserver observer) {
        String player = observer.getPlayer();
        AwaitingDecision awaitingDecision = _awaitingDecisionMap.get(player);
        observer.setPlayerDecision(awaitingDecision);
    }
}
