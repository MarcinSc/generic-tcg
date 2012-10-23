package com.gempukku.tcg.generic;

import com.gempukku.tcg.Deck;
import com.gempukku.tcg.GameDefinition;
import com.gempukku.tcg.GameLogic;
import com.gempukku.tcg.generic.actions.Action;
import com.gempukku.tcg.generic.actions.EventAction;
import com.gempukku.tcg.generic.events.SetGameObjectEvent;
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

    public ActionStackSpringGameDefinition(String springContext, String rootActionName, String gameFinishConditionName) {
        _rootActionName = rootActionName;
        _gameFinishConditionName = gameFinishConditionName;
        _applicationContext = new ClassPathXmlApplicationContext(springContext);
    }

    @Override
    public GameLogic createGameLogic(Map<String, String> parameters, Map<String, Deck> playerDecks) {
        Action rootAction = (Action) _applicationContext.getBean(_rootActionName);
        Condition gameFinishCondition = (Condition) _applicationContext.getBean(_gameFinishConditionName);

        ActionStackGameLogic actionStackGameLogic = new ActionStackGameLogic(rootAction, gameFinishCondition);
        
        EventAction eventAction = new EventAction();
        eventAction.addEvent(new SetGameObjectEvent(_gameParametersLocation, parameters));
        eventAction.addEvent(new SetGameObjectEvent(_playersLocation, playerDecks.keySet()));
        eventAction.addEvent(new SetGameObjectEvent(_decksLocation, playerDecks));
        actionStackGameLogic.getActionStack().stackAction(eventAction);

        return actionStackGameLogic;
    }
}
