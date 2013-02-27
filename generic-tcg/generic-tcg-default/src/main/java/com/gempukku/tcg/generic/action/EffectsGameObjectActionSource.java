package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.GameObjectEffect;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.LinkedList;
import java.util.List;

public class EffectsGameObjectActionSource implements GameObjectActionSource {
    private List<GameObjectEffect> _effects = new LinkedList<GameObjectEffect>();

    public void setEffects(List<GameObjectEffect> effects) {
        _effects.addAll(effects);
    }

    public void addEffect(GameObjectEffect effect) {
        _effects.add(effect);
    }

    @Override
    public GameAction createGameAction(GameState gameState, GameObject gameObject) {
        return new EffectsGameObjectAction(gameObject, _effects);
    }
}
