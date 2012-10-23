package com.gempukku.tcg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameMediator<T extends GameStateObserver> {
    private GameLogic _gameLogic;
    private GameState _gameState = new GameState();
    private UserFeedback _userFeedback = new UserFeedback();
    private Map<String, T> _gameStateObservers = new HashMap<String, T>();

    private ReadWriteLock _lock = new ReentrantReadWriteLock(true);

    public GameMediator(GameLogic gameLogic) {
        _gameLogic = gameLogic;
    }

    public void startGame() {
        _lock.writeLock().lock();
        try {
            processUntilDecisionOrFinished();
        } finally {
            _lock.writeLock().unlock();
        }
    }

    protected GameState getGameState() {
        return _gameState;
    }

    protected ReadWriteLock getLock() {
        return _lock;
    }

    public void playerResponded(String player, String answer) throws InvalidDecisionException {
        _lock.writeLock().lock();
        try {
            AwaitingDecision decision = _userFeedback.removePlayerDecision(player);
            if (decision != null) {
                try {
                    decision.playerDecided(answer);
                    processUntilDecisionOrFinished();
                } catch (InvalidDecisionException exp) {
                    _userFeedback.sendDecision(player, decision);
                    throw exp;
                }
            }
        } finally {
            _lock.writeLock().unlock();
        }
    }

    private void processUntilDecisionOrFinished() {
        while (!_userFeedback.hasAwaitingDecisions()
                && !_gameLogic.isFinished(_gameState, _userFeedback)) {
            _gameLogic.proceed(_gameState, _userFeedback);
            for (GameStateObserver gameStateObserver : _gameStateObservers.values())
                gameStateObserver.gameStateChanged(_gameState);
        }
    }

    public void consumeChanges(String player, GameMediatorVisitor<T> gameMediatorVisitor) throws PlayerTimeoutException {
        _lock.readLock().lock();
        try {
            T t = _gameStateObservers.get(player);
            if (t == null)
                throw new PlayerTimeoutException();

            gameMediatorVisitor.consumeChanges(t);
        } finally {
            _lock.readLock().unlock();
        }
    }
}
