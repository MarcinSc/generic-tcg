package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.GameObjects;

import java.util.List;

public class ConcatenateStringEvaluator implements StringEvaluator {
    private List<StringEvaluator> _stringEvaluators;

    public void setStringEvaluators(List<StringEvaluator> stringEvaluators) {
        _stringEvaluators = stringEvaluators;
    }

    @Override
    public String getValue(GameObjects gameObjects, GameActionContext context) {
        StringBuilder sb = new StringBuilder();
        for (StringEvaluator stringEvaluator : _stringEvaluators)
            sb.append(stringEvaluator.getValue(gameObjects, context));

        return sb.toString();
    }
}
