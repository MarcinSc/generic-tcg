package com.gempukku.tcg.quest;

import com.gempukku.tcg.GameStateObserverFactory;

public class QuestGameStateObserverFactory implements GameStateObserverFactory<QuestGameStateObserver> {
    @Override
    public QuestGameStateObserver createGameStateObserver(String player) {
        return new QuestGameStateObserver(player);
    }
}
