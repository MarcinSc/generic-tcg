package com.gempukku.tcg.generic.keyword;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.modifier.GameModifierConsumer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class KeywordManager implements GameModifierConsumer<KeywordModifier> {
    private Set<KeywordModifier> _keywordModifiers = new LinkedHashSet<KeywordModifier>();

    public void setKeywordModifiers(List<KeywordModifier> keywordModifiers) {
        _keywordModifiers.addAll(keywordModifiers);
    }

    @Override
    public void registerModifier(KeywordModifier gameModifier) {
        _keywordModifiers.add(gameModifier);
    }

    @Override
    public void removeModifier(KeywordModifier gameModifier) {
        _keywordModifiers.remove(gameModifier);
    }

    public boolean hasKeyword(GameObjects gameState, GameActionContext gameObject, String keyword) {
        for (KeywordModifier keywordModifier : _keywordModifiers) {
            if (keywordModifier.hasKeyword(gameState, gameObject, keyword))
                return true;
        }
        return false;
    }

    public int getKeywordCount(GameObjects gameState, GameActionContext gameObject, String keyword) {
        int count = 0;
        for (KeywordModifier keywordModifier : _keywordModifiers)
            count += keywordModifier.getKeywordCount(gameState, gameObject, keyword);
        return count;
    }
}
