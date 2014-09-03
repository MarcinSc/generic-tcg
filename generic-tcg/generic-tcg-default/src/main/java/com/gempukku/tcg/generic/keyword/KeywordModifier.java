package com.gempukku.tcg.generic.keyword;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.modifier.GameModifier;

public interface KeywordModifier extends GameModifier {
    public boolean hasKeyword(GameObjects gameState, GameActionContext gameObject, String keyword);

    public int getKeywordCount(GameObjects gameState, GameActionContext gameObject, String keyword);
}
