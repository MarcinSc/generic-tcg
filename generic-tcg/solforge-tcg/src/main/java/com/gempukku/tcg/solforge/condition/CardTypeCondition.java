package com.gempukku.tcg.solforge.condition;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.condition.GameObjectCondition;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.solforge.SolforgeObjects;

public class CardTypeCondition implements GameObjectCondition {
    private String _cardType;

    public void setCardType(String cardType) {
        _cardType = cardType;
    }

    @Override
    public boolean isMet(GameState gameState, GameObject gameObject) {
        return SolforgeObjects.extractGameObject(gameState, SolforgeObjects.OBJECT_RESOLVER)
                .getCardBlueprint(gameObject.getProperty("blueprintId"))
                .getCardLevelBlueprintId(Integer.parseInt(gameObject.getProperty("level")))
                .getCardType().equals(_cardType);
    }
}
