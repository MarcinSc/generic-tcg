package com.gempukku.tcg.solforge.stat;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.SolforgeObjects;

public class BlueprintAttackBase implements IntEvaluator {
    @Override
    public int getValue(GameState gameState, GameObject gameObject) {
        return SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                .getCardBlueprint(gameObject.getProperty("blueprintId"))
                .getCardLevelBlueprintId(Integer.parseInt(gameObject.getProperty("level")))
                .getAttack();
    }
}
