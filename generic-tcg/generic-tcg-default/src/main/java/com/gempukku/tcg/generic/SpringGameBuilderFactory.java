package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameState;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class SpringGameBuilderFactory implements GameBuilderFactory {
    private String _contextPath;
    private String _gameStateBeanName;
    private String _gameProcessorBeanName;

    public void setContextPath(String contextPath) {
        _contextPath = contextPath;
    }

    public void setGameStateBeanName(String gameStateBeanName) {
        _gameStateBeanName = gameStateBeanName;
    }

    public void setGameProcessorBeanName(String gameProcessorBeanName) {
        _gameProcessorBeanName = gameProcessorBeanName;
    }

    @Override
    public GameBuilder createGameBuilder(Map<String, GameDeck> playersAndDecks) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(_contextPath);
        GameState gameState = applicationContext.getBean(_gameStateBeanName, GameState.class);
        PlayerDeckGameProcessor gameProcessor = applicationContext.getBean(_gameProcessorBeanName, PlayerDeckGameProcessor.class);
        gameProcessor.startProcessing(gameState, playersAndDecks);

        return new SimpleGameBuilder(gameState, gameProcessor);
    }
}
