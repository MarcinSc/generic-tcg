package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.GenericContextObjects;

public class KeywordCountEvaluator implements IntEvaluator {
    private String _keywordManager;
    private String _keyword;
    private Integer _minValue;
    private Integer _maxValue;

    public void setKeyword(String keyword) {
        _keyword = keyword;
    }

    public void setKeywordManager(String keywordManager) {
        _keywordManager = keywordManager;
    }

    public void setMinValue(Integer minValue) {
        _minValue = minValue;
    }

    public void setMaxValue(Integer maxValue) {
        _maxValue = maxValue;
    }

    @Override
    public int getValue(GameObjects gameObjects, GameActionContext context) {
        int result = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.KEYWORD_MANAGER).getKeywordCount(gameObjects, context, _keyword);
        if (_minValue != null)
            result = Math.max(_minValue, result);
        if (_maxValue != null)
            result = Math.min(_maxValue, result);
        return result;
    }
}
