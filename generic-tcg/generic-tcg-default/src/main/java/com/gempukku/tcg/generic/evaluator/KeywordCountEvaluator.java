package com.gempukku.tcg.generic.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.keyword.KeywordManager;
import com.gempukku.tcg.generic.object.GameObject;

public class KeywordCountEvaluator implements IntEvaluator {
    private String _keywordManager;
    private String _keyword;

    public void setKeyword(String keyword) {
        _keyword = keyword;
    }

    public void setKeywordManager(String keywordManager) {
        _keywordManager = keywordManager;
    }

    @Override
    public int getValue(GameState gameState, GameObject gameObject) {
        return ((KeywordManager) gameState.getGameObject(_keywordManager)).getKeywordCount(gameState, gameObject, _keyword);
    }
}
