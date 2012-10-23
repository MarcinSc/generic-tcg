package com.gempukku.tcg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer {
    private Map<String, GameMediator> _gameMediators = new ConcurrentHashMap<String, GameMediator>();
    private AtomicInteger _gameId = new AtomicInteger(0);

    public GameMediator getGameMediator(String gameId) {
        return _gameMediators.get(gameId);
    }

    public String addGameMediator(GameMediator gameMediator) {
        String gameId = String.valueOf(_gameId.incrementAndGet());
        _gameMediators.put(gameId, gameMediator);
        return gameId;
    }

    public void removeGameMediator(String gameId) {
        _gameMediators.remove(gameId);
    }
}
