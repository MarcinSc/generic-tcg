package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.modifier.ActionModifier;
import com.gempukku.tcg.generic.modifier.GameModifierConsumer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class GameActionManager implements GameModifierConsumer<ActionModifier> {
    private List<ActionModifier> _actionModifiers = new LinkedList<ActionModifier>();

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

    public Collection<GameAction> getActions(GameState gameState) {
        List<GameAction> actions = new LinkedList<GameAction>();
        for (ActionModifier actionModifier : _actionModifiers)
            actions.addAll(actionModifier.getPossibleActions(gameState));

        return actions;
    }
}
