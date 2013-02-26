package com.gempukku.tcg.generic;

import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.modifier.GameModifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.Map;

public class SpringGameBuilderFactory implements GameBuilderFactory {
    private String _contextPath;
    private String _gameStateBeanName;
    private String _gameProcessorBeanName;
    private List<GameModifier> _alwaysOnGameModifiers;
    private Object _objectResolver;

    public void setContextPath(String contextPath) {
        _contextPath = contextPath;
    }

    public void setGameStateBeanName(String gameStateBeanName) {
        _gameStateBeanName = gameStateBeanName;
    }

    public void setGameProcessorBeanName(String gameProcessorBeanName) {
        _gameProcessorBeanName = gameProcessorBeanName;
    }

    public void setAlwaysOnGameModifiers(List<GameModifier> alwaysOnGameModifiers) {
        _alwaysOnGameModifiers = alwaysOnGameModifiers;
    }

    public void setObjectResolver(Object objectResolver) {
        _objectResolver = objectResolver;
    }

    @Override
    public GameBuilder createGameBuilder(Map<String, GameDeck> playersAndDecks) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(_contextPath);

        GameState gameState = applicationContext.getBean(_gameStateBeanName, GameState.class);
        PlayerDeckGameProcessor gameProcessor = applicationContext.getBean(_gameProcessorBeanName, PlayerDeckGameProcessor.class);

        gameProcessor.startProcessing(gameState, _objectResolver, _alwaysOnGameModifiers, playersAndDecks);

        return new SimpleGameBuilder(gameState, gameProcessor);
    }
}
