package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.GameObjectEffect;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.List;

public class EffectsGameObjectActionSource implements GameObjectActionSource {
    private List<GameObjectEffect> _effects;

    public void setEffects(List<GameObjectEffect> effects) {
        _effects = effects;
    }

    @Override
    public GameAction createGameAction(GameState gameState, GameObject gameObject) {
        return new EffectsGameObjectAction(gameObject, _effects);
    }
}
