package com.gempukku.tcg.generic;

import com.gempukku.tcg.*;
import com.gempukku.tcg.generic.actions.Action;
import com.gempukku.tcg.generic.actions.EventAction;
import com.gempukku.tcg.generic.events.SetGameObjectEvent;
import com.gempukku.tcg.generic.events.SetPlayerObjectsEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class ActionStackSpringGameDefinition<T> implements GameDefinition<T> {
    private ApplicationContext _applicationContext;
    private String _gameParametersLocation;
    private String _playersLocation;
    private String _decksLocation;
    private String _rootActionName;
    private String _gameFinishConditionName;
    private String _gameStateObserverFactoryName;
    private String _gameStateEvaluatorName;

    public ActionStackSpringGameDefinition(String springContext, String rootActionName, String gameFinishConditionName,
                                           String gameStateObserverFactoryName, String gameStateEvaluatorName) {
        _rootActionName = rootActionName;
        _gameFinishConditionName = gameFinishConditionName;
        _gameStateObserverFactoryName = gameStateObserverFactoryName;
        _gameStateEvaluatorName = gameStateEvaluatorName;
        _applicationContext = new ClassPathXmlApplicationContext(springContext);
    }

    public void setGameParametersLocation(String gameParametersLocation) {
        _gameParametersLocation = gameParametersLocation;
    }

    public void setPlayersLocation(String playersLocation) {
        _playersLocation = playersLocation;
    }

    public void setDecksLocation(String decksLocation) {
        _decksLocation = decksLocation;
    }

    @Override
    public GameLogic createGameLogic(Map<String, String> parameters, Map<String, Deck> playerDecks) {
        Action rootAction = (Action) _applicationContext.getBean(_rootActionName);
        Condition gameFinishCondition = (Condition) _applicationContext.getBean(_gameFinishConditionName);

        ActionStackGameLogic actionStackGameLogic = new ActionStackGameLogic(rootAction, gameFinishCondition);

        EventAction eventAction = new EventAction();
        eventAction.addEvent(new SetGameObjectEvent(_gameParametersLocation, parameters));
        eventAction.addEvent(new SetGameObjectEvent(_playersLocation, playerDecks.keySet()));
        eventAction.addEvent(new SetPlayerObjectsEvent(_decksLocation, playerDecks));
        actionStackGameLogic.getActionStack().stackAction(eventAction);

        return actionStackGameLogic;
    }

    @Override
    public GameStateEvaluator<T> createGameStateEvaluator() {
        return (GameStateEvaluator<T>) _applicationContext.getBean(_gameStateEvaluatorName);
    }

    @Override
    public GameStateObserverFactory<T> createGameStateObserverFactory() {
        return (GameStateObserverFactory<T>) _applicationContext.getBean(_gameStateObserverFactoryName);
    }
}
