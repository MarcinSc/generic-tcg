package com.gempukku.tcg.generic;

import com.gempukku.tcg.generic.decision.DecisionHolder;
import com.gempukku.tcg.GameBuilder;
import com.gempukku.tcg.GameDeck;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.GameObjectsFactory;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.generic.decision.DecisionHolderFactory;
import com.gempukku.tcg.generic.environment.MapDigitalEnvironment;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;
import java.util.Set;

public class SpringGameBuilderFactory implements GameBuilderFactory {
    private String _contextPath;
    private String _gameObjectsFactoryBeanName;
    private String _gameProcessorBeanName;
    private String _decisionHolderFactoryBeanName;
    private ClassPathXmlApplicationContext _applicationContext;

    public void setContextPath(String contextPath) {
        _contextPath = contextPath;
    }

    public void setGameObjectsFactoryBeanName(String gameObjectsFactoryBeanName) {
        _gameObjectsFactoryBeanName = gameObjectsFactoryBeanName;
    }

    public void setGameProcessorBeanName(String gameProcessorBeanName) {
        _gameProcessorBeanName = gameProcessorBeanName;
    }

    public void setDecisionHolderFactoryBeanName(String decisionHolderFactoryBeanName) {
        _decisionHolderFactoryBeanName = decisionHolderFactoryBeanName;
    }

    public void initialize() {
        _applicationContext = new ClassPathXmlApplicationContext(_contextPath);
    }

    @Override
    public GameBuilder startNewGame(Map<String, GameDeck> playersAndDecks) {
        GameObjectsFactory gameObjectsFactory = _applicationContext.getBean(_gameObjectsFactoryBeanName, GameObjectsFactory.class);
        PlayerDeckGameProcessor gameProcessor = _applicationContext.getBean(_gameProcessorBeanName, PlayerDeckGameProcessor.class);

        final GameObjects gameObjects = gameObjectsFactory.create();

        final DigitalEnvironment digitalEnvironment = new MapDigitalEnvironment();
        gameProcessor.startProcessing(gameObjects, digitalEnvironment, playersAndDecks);

        return new SimpleGameBuilder(gameObjects, gameProcessor, digitalEnvironment);
    }

    @Override
    public GameBuilder continueLoadedGame(DigitalEnvironment digitalEnvironment, Set<String> players) {
        GameObjectsFactory gameObjectsFactory = _applicationContext.getBean(_gameObjectsFactoryBeanName, GameObjectsFactory.class);
        PlayerDeckGameProcessor gameProcessor = _applicationContext.getBean(_gameProcessorBeanName, PlayerDeckGameProcessor.class);

        final GameObjects gameObjects = gameObjectsFactory.create();

        gameProcessor.startProcessingLoaded(gameObjects, digitalEnvironment, players);

        return new SimpleGameBuilder(gameObjects, gameProcessor, digitalEnvironment);
    }
}
