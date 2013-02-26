package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.effect.GameObjectEffectSerie;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.List;

public class EffectsGameObjectActionPossibility implements GameActionPossibility{
    private List<GameObjectEffectSerie> _effects;
    private GameObject _gameObject;
    private StringEvaluator _textEvaluator;

    public EffectsGameObjectActionPossibility(GameObject gameObject, List<GameObjectEffectSerie> effects, StringEvaluator textEvaluator) {
        _gameObject = gameObject;
        _effects = effects;
        _textEvaluator = textEvaluator;
    }

    @Override
    public String getText(GameState gameState) {
        return _textEvaluator.getValue(gameState, _gameObject);
    }

    @Override
    public String getAttachedObjectId(GameState gameState) {
        return _gameObject.getIdentifier();
    }

    @Override
    public GameAction createGameAction(GameState gameState) {
        return new EffectsGameObjectAction(_gameObject, _effects);
    }
}
