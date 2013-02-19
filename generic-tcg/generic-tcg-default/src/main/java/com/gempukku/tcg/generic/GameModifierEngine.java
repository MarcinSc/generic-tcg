package com.gempukku.tcg.generic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GameModifierEngine implements GameEventListener {
    private Map<Class<?>, GameModifierConsumer> _consumerMap = new HashMap<Class<?>, GameModifierConsumer>();
    private Map<GameModifier, GameEventCondition> _modifierRemovalConditions = new HashMap<GameModifier, GameEventCondition>();

    public void setGameModifierMap(Map<String, GameModifierConsumer<?>> gameModifierMap) throws ClassNotFoundException {
        for (Map.Entry<String, GameModifierConsumer<?>> gameModifierMapping : gameModifierMap.entrySet())
            _consumerMap.put(Class.forName(gameModifierMapping.getKey()), gameModifierMapping.getValue());
    }

    public void addGameModifier(GameModifier gameModifier, GameEventCondition removeCondition) {
        boolean found = false;
        for (Map.Entry<Class<?>, GameModifierConsumer> modifierMapping : _consumerMap.entrySet()) {
            if (modifierMapping.getKey().isInstance(gameModifier)) {
                modifierMapping.getValue().registerModifier(gameModifier);
                found = true;
            }
        }

        if (!found)
            throw new IllegalArgumentException("Unknown gameModifier class: " + gameModifier.getClass());

        _modifierRemovalConditions.put(gameModifier, removeCondition);
    }

    @Override
    public void processGameEvent(GameEvent gameEvent) {
        final Iterator<Map.Entry<GameModifier, GameEventCondition>> modifierRemovalConditionIterator = _modifierRemovalConditions.entrySet().iterator();
        while (modifierRemovalConditionIterator.hasNext()) {
            final Map.Entry<GameModifier, GameEventCondition> modifierRemovalCondition = modifierRemovalConditionIterator.next();
            if (modifierRemovalCondition.getValue().matches(gameEvent)) {
                final GameModifier gameModifier = modifierRemovalCondition.getKey();
                for (Map.Entry<Class<?>, GameModifierConsumer> modifierMapping : _consumerMap.entrySet()) {
                    if (modifierMapping.getKey().isInstance(gameModifier))
                        modifierMapping.getValue().removeModifier(gameModifier);
                }
                modifierRemovalConditionIterator.remove();
            }
        }
    }
}
