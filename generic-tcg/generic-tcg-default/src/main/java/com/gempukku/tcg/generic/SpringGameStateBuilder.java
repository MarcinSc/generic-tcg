package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameState;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Set;

public class SpringGameStateBuilder implements GameStateBuilder {
    private String _contextPath;
    private String _gameStateBeanName;

    public void setContextPath(String contextPath) {
        _contextPath = contextPath;
    }

    public void setGameStateBeanName(String gameStateBeanName) {
        _gameStateBeanName = gameStateBeanName;
    }

    @Override
    public GameState createGameState(Set<String> players) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(_contextPath);
        return applicationContext.getBean(_gameStateBeanName, GameState.class);
    }
}
