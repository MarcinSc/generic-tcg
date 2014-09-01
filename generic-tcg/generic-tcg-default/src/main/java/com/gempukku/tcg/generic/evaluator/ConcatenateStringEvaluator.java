package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

import java.util.List;

public class ConcatenateStringEvaluator implements StringEvaluator {
    private List<StringEvaluator> _stringEvaluators;

    public void setStringEvaluators(List<StringEvaluator> stringEvaluators) {
        _stringEvaluators = stringEvaluators;
    }

    @Override
    public String getValue(GameObjects gameState, DigitalObject digitalObject) {
        StringBuilder sb = new StringBuilder();
        for (StringEvaluator stringEvaluator : _stringEvaluators)
            sb.append(stringEvaluator.getValue(gameState, digitalObject));

        return sb.toString();
    }
}
