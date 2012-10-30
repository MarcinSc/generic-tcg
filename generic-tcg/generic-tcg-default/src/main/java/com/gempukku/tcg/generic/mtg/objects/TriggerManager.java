package com.gempukku.tcg.generic.mtg.objects;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.mtg.MtgConstants;
import com.gempukku.tcg.generic.objects.Card;
import com.gempukku.tcg.generic.objects.CardContainer;
import com.gempukku.tcg.generic.objects.CardZone;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TriggerManager {
    private TriggerContainer _triggerContainer = new TriggerContainer();

    private List<GameEventListener> _gameEventListeners = new LinkedList<GameEventListener>();
    private BlueprintTriggerResolver _blueprintTriggerResolver;

    public void setGameEventListeners(Set<GameEventListener> gameEventListeners) {
        _gameEventListeners.addAll(gameEventListeners);
    }

    public void setBlueprintTriggerResolver(BlueprintTriggerResolver blueprintTriggerResolver) {
        _blueprintTriggerResolver = blueprintTriggerResolver;
    }

    public void addGameEventListener(GameEventListener gameEventListener) {
        _gameEventListeners.add(gameEventListener);
    }

    public void removeGameEventListener(GameEventListener gameEventListener) {
        _gameEventListeners.remove(gameEventListener);
    }

    public void distributeGameEvent(GameState gameState, String type, Map<String, String> params) {
        CardContainer cardContainer = (CardContainer) gameState.getGameObject(MtgConstants.CARD_CONTAINER);
        CardZone play = cardContainer.getSharedCardZone(MtgConstants.PLAY);
        for (Card card : play.getCards()) {
            String blueprint = card.getProperty(MtgConstants.CARD_BLUEPRINT);
            List<TriggerCondition> triggerConditions = _blueprintTriggerResolver.getTriggerConditions(blueprint);
            for (TriggerCondition triggerCondition : triggerConditions) {
                Trigger trigger = triggerCondition.generateTrigger(gameState, type, params);
                if (trigger != null)
                    _triggerContainer.addWaitingTrigger(trigger);
            }
        }

        for (GameEventListener gameEventListener : _gameEventListeners)
            gameEventListener.triggerHappened(gameState, _triggerContainer, type, params);
    }

    public Set<Trigger> consumeAwaitingTriggers() {
        return _triggerContainer.consumeWaitingTriggers();
    }
}
