package com.gempukku.tcg.generic.stat;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.modifier.GameModifierConsumer;

import java.util.LinkedHashSet;
import java.util.Set;

public class StatManager implements GameModifierConsumer<StatModifier> {
    private Set<StatModifier> _statModifiers = new LinkedHashSet<StatModifier>();
    private IntEvaluator _baseEvaluator;

    public void setBaseEvaluator(IntEvaluator baseEvaluator) {
        _baseEvaluator = baseEvaluator;
    }

    @Override
    public void registerModifier(StatModifier gameModifier) {
        _statModifiers.add(gameModifier);
    }

    @Override
    public void removeModifier(StatModifier gameModifier) {
        _statModifiers.remove(gameModifier);
    }

    public int getStatValue(GameObjects gameObjects, GameActionContext context) {
        int value = _baseEvaluator.getIntValue(gameObjects, context);
        for (StatModifier statModifier : _statModifiers)
            value = statModifier.applyModifier(gameObjects, context, value);

        return value;
    }
}
