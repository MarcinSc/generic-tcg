package com.gempukku.tcg.solforge.evaluator;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.Solforge;
import com.gempukku.tcg.solforge.SolforgeObjects;

public class CardNameEvaluator implements StringEvaluator {
    @Override
    public String getValue(GameState gameState, GameObject gameObject) {
        return SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                .getCardBlueprint(gameObject.getProperty(Solforge.Properties.BLUEPRINT_ID)).getName();
    }
}
