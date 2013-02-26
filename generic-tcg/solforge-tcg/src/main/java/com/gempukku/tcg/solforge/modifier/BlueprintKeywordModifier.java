package com.gempukku.tcg.solforge.modifier;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.keyword.AdditiveKeyword;
import com.gempukku.tcg.generic.keyword.Keyword;
import com.gempukku.tcg.generic.keyword.KeywordModifier;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.SolforgeObjects;

import java.util.List;

public class BlueprintKeywordModifier implements KeywordModifier {
    @Override
    public boolean hasKeyword(GameState gameState, GameObject gameObject, String keyword) {
        final List<Keyword> keywords = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                .getCardBlueprint(gameObject.getProperty("blueprintId"))
                .getCardLevelBlueprintId(Integer.parseInt(gameObject.getProperty("level")))
                .getKeywords();
        if (keywords != null) {
            for (Keyword cardKeyword : keywords) {
                if (cardKeyword.getName().equals(keyword))
                    return true;
            }
        }
        return false;
    }

    @Override
    public int getKeywordCount(GameState gameState, GameObject gameObject, String keyword) {
        final List<Keyword> keywords = SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                .getCardBlueprint(gameObject.getProperty("blueprintId"))
                .getCardLevelBlueprintId(Integer.parseInt(gameObject.getProperty("level")))
                .getKeywords();
        if (keywords != null) {
            for (Keyword cardKeyword : keywords) {
                if (cardKeyword instanceof AdditiveKeyword)
                    return ((AdditiveKeyword) cardKeyword).getValue();
            }
        }
        return 0;
    }
}
