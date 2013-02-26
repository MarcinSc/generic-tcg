package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

import java.util.List;

public class ConcatenateStringEvaluator implements StringEvaluator {
    private List<StringEvaluator> _stringEvaluators;

    public void setStringEvaluators(List<StringEvaluator> stringEvaluators) {
        _stringEvaluators = stringEvaluators;
    }

    @Override
    public String getValue(GameState gameState, GameObject gameObject) {
        StringBuilder sb = new StringBuilder();
        for (StringEvaluator stringEvaluator : _stringEvaluators)
            sb.append(stringEvaluator.getValue(gameState, gameObject));

        return sb.toString();
    }
}
