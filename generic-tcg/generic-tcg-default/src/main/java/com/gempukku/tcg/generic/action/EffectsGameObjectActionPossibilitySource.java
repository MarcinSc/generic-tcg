package com.gempukku.tcg.generic.action;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameObjectEffectSerie;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;

import java.util.List;

public class EffectsGameObjectActionPossibilitySource implements GameObjectActionPossibilitySource {
    private List<GameObjectEffectSerie> _effects;
    private StringEvaluator _textEvaluator;

    public void setEffects(List<GameObjectEffectSerie> effects) {
        _effects = effects;
    }

    public void setTextEvaluator(StringEvaluator textEvaluator) {
        _textEvaluator = textEvaluator;
    }

    @Override
    public boolean isPlayable(GameState gameState, DigitalObject fromObject) {
        return true;
    }

    @Override
    public GameActionPossibility getGameActionPossibility(GameState gameState, DigitalObject fromObject) {
        return new EffectsGameObjectActionPossibility(fromObject, _effects, _textEvaluator);
    }
}
