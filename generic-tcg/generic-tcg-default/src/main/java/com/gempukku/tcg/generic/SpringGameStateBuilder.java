package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SpringGameStateBuilder implements GameStateBuilder, BeanFactoryAware {
    private BeanFactory _beanFactory;
    private Map<String, String> _gameObjects = new HashMap<String, String>();
    private Map<String, String> _playerObjects = new HashMap<String, String>();

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        _beanFactory = beanFactory;
    }

    public void setGameObjects(Map<String, String> gameObjects) {
        _gameObjects = gameObjects;
    }

    public void setPlayerObjects(Map<String, String> playerObjects) {
        _playerObjects = playerObjects;
    }

    @Override
    public GameState createGameState(Set<String> players) {
        GameState gameState = new GameState();
        for (Map.Entry<String, String> gameObject : _gameObjects.entrySet())
            gameState.setGameObject(gameObject.getKey(), _beanFactory.getBean(gameObject.getValue()));
        for (String player : players)
            for (Map.Entry<String, String> playerObject : _playerObjects.entrySet())
                gameState.setPlayerObject(player, playerObject.getKey(), _beanFactory.getBean(playerObject.getValue()));

        return gameState;
    }
}
