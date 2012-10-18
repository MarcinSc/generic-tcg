package com.gempukku.tcg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameServer {
    private Map<String, XmlGame> _games = new ConcurrentHashMap<String, XmlGame>();

    public XmlGame getGame(String gameId) {
        return _games.get(gameId);
    }
}
