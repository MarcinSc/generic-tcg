package com.gempukku.tcg.generic.keyword;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.modifier.GameModifier;

public interface KeywordModifier extends GameModifier {
    public boolean hasKeyword(GameObjects gameState, DigitalObject gameObject, String keyword);

    public int getKeywordCount(GameObjects gameState, DigitalObject gameObject, String keyword);
}
