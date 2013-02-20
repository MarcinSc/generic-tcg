package com.gempukku.tcg;

public class GameMediator {
    private GameState _gameState;
    private GameProcessor _gameProcessor;

    public GameMediator(GameState gameState, GameProcessor gameProcessor) {
        _gameState = gameState;
        _gameProcessor = gameProcessor;
    }

    public void playerSentDecision(String player, String decision) {
        _gameProcessor.playerSentDecision(_gameState, player, decision);
    }
}
