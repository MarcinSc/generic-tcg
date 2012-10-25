package com.gempukku.tcg.generic;

import com.gempukku.tcg.Deck;
import com.gempukku.tcg.GameDefinition;
import com.gempukku.tcg.GameLogic;
import com.gempukku.tcg.GameStateObserver;
import com.gempukku.tcg.generic.actions.Action;
import com.gempukku.tcg.generic.actions.EventAction;
import com.gempukku.tcg.generic.events.SetGameObjectEvent;
import com.gempukku.tcg.generic.events.SetPlayerObjectsEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class ActionStackSpringGameDefinition implements GameDefinition {
    private ApplicationContext _applicationContext;
    private String _gameParametersLocation;
    private String _playersLocation;
    private String _decksLocation;
    private String _rootActionName;
    private String _gameFinishConditionName;
    private String _gameStateObserverName;

    public ActionStackSpringGameDefinition(String springContext, String rootActionName, String gameFinishConditionName,
    String gameStateObserverName) {
        _rootActionName = rootActionName;
        _gameFinishConditionName = gameFinishConditionName;
        _gameStateObserverName = gameStateObserverName;
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
    public GameStateObserver createGameStateObserver() {
        return (GameStateObserver) _applicationContext.getBean(_gameStateObserverName);
    }
}
