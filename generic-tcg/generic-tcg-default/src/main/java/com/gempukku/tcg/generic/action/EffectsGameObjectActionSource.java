package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.DigitalObjectEffect;

import java.util.LinkedList;
import java.util.List;

public class EffectsGameObjectActionSource implements GameObjectActionSource {
    private List<DigitalObjectEffect> _effects = new LinkedList<DigitalObjectEffect>();

    public void setEffects(List<DigitalObjectEffect> effects) {
        _effects.addAll(effects);
    }

    public void addEffect(DigitalObjectEffect effect) {
        _effects.add(effect);
    }

    @Override
    public GameAction createGameAction(GameObjects gameState, DigitalObject digitalObject) {
        return new EffectsGameObjectAction(digitalObject, _effects);
    }
}
