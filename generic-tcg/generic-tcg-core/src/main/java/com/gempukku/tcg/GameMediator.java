package com.gempukku.tcg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameMediator<T> {
    private GameLogic _gameLogic;
    private GameStateEvaluator<T> _gameStateEvaluator;
    private GameStateObserverFactory<T> _gameStateObserverFactory;
    private GameState _gameState = new GameState();
    private UserFeedback _userFeedback = new UserFeedback();
    private Map<String, T> _gameStateObservers = new HashMap<String, T>();

    private ReadWriteLock _lock = new ReentrantReadWriteLock(true);

    public GameMediator(GameLogic gameLogic, GameStateEvaluator<T> gameStateEvaluator, GameStateObserverFactory<T> gameStateObserverFactory) {
        _gameLogic = gameLogic;
        _gameStateEvaluator = gameStateEvaluator;
        _gameStateObserverFactory = gameStateObserverFactory;
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

    public void signForObserving(String player) {
        _lock.writeLock().lock();
        try {
            T gameStateObserver = _gameStateObserverFactory.createGameStateObserver(player);
            _gameStateObservers.put(player, gameStateObserver);
            _gameStateEvaluator.addGameStateObserver(gameStateObserver);
        } finally {
            _lock.writeLock().unlock();
        }
    }

    private void processUntilDecisionOrFinished() {
        while (!_userFeedback.hasAwaitingDecisions()
                && !_gameLogic.isFinished(_gameState, _userFeedback)) {
            _gameLogic.proceed(_gameState, _userFeedback);
            _gameStateEvaluator.gameStateChanged(_gameState, _userFeedback);
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
