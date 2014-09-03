package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.modifier.ActionModifier;
import com.gempukku.tcg.generic.modifier.GameModifierConsumer;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class GameActionManager implements GameModifierConsumer<ActionModifier> {
    private Set<ActionModifier> _actionModifiers = new LinkedHashSet<ActionModifier>();

    public void setActionModifiers(List<ActionModifier> actionModifiers) {
        _actionModifiers.addAll(actionModifiers);
    }

    @Override
    public void registerModifier(ActionModifier gameModifier) {
        _actionModifiers.add(gameModifier);
    }

    @Override
    public void removeModifier(ActionModifier gameModifier) {
        _actionModifiers.remove(gameModifier);
    }

    public Collection<GameActionPossibility> getPossibleActions(GameObjects gameState) {
        List<GameActionPossibility> actions = new LinkedList<GameActionPossibility>();
        for (ActionModifier actionModifier : _actionModifiers)
            actions.addAll(actionModifier.getPossibleActions(gameState));

        return actions;
    }
}
