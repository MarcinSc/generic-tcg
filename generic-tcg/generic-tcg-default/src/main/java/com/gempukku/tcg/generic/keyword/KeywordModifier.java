package com.gempukku.tcg.generic.keyword;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.modifier.GameModifier;
import com.gempukku.tcg.generic.object.GameObject;

public interface KeywordModifier extends GameModifier {
    public boolean hasKeyword(GameState gameState, GameObject gameObject, String keyword);

    public int getKeywordCount(GameState gameState, GameObject gameObject, String keyword);
}
