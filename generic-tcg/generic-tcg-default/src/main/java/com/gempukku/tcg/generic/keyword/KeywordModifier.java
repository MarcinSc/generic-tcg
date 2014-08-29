package com.gempukku.tcg.generic.keyword;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.modifier.GameModifier;

public interface KeywordModifier extends GameModifier {
    public boolean hasKeyword(GameState gameState, DigitalObject gameObject, String keyword);

    public int getKeywordCount(GameState gameState, DigitalObject gameObject, String keyword);
}
