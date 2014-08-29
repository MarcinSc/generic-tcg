package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
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
    public boolean hasNextGameEffect(GameState gameState) {
        return _nextIndex < _gameObjectEffects.size();
    }

    @Override
    public void processNextGameEffect(GameState gameState) {
        int indexToExecute = _nextIndex;
        boolean repeat = _gameObjectEffects.get(indexToExecute).execute(gameState, _gameObject);
        if (!repeat)
            _nextIndex++;
    }
}
