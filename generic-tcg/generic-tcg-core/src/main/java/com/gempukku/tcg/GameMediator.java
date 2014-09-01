package com.gempukku.tcg;

public class GameMediator {
    private GameObjects _gameObjects;
    private GameProcessor _gameProcessor;

    public GameMediator(GameObjects gameObjects, GameProcessor gameProcessor) {
        _gameObjects = gameObjects;
        _gameProcessor = gameProcessor;
    }

    public void playerSentDecision(String player, String decision) {
        _gameProcessor.playerSentDecision(_gameObjects, player, decision);
    }
}
