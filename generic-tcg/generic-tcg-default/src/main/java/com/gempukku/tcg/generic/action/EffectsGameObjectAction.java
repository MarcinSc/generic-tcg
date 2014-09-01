package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameObjectEffectSerie;

import java.util.List;

public class EffectsGameObjectAction implements GameAction {
    private DigitalObject _gameObject;
    private List<? extends GameObjectEffectSerie> _gameObjectEffects;
    private int _nextIndex = 0;

    public EffectsGameObjectAction(DigitalObject gameObject, List<? extends GameObjectEffectSerie> gameObjectEffects) {
        _gameObject = gameObject;
        _gameObjectEffects = gameObjectEffects;
    }

    @Override
    public boolean hasNextGameEffect(GameObjects gameState) {
        return _nextIndex < _gameObjectEffects.size();
    }

    @Override
    public void processNextGameEffect(GameObjects gameState) {
        int indexToExecute = _nextIndex;
        boolean repeat = _gameObjectEffects.get(indexToExecute).execute(gameState, _gameObject);
        if (!repeat)
            _nextIndex++;
    }
}
