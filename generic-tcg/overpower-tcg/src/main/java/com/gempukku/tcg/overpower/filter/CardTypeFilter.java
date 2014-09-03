package com.gempukku.tcg.overpower.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.overpower.OverpowerContextObjects;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;

public class CardTypeFilter implements DigitalObjectFilter {
    private String _cardType;

    public CardTypeFilter(String cardType) {
        _cardType = cardType;
    }

    @Override
    public boolean accept(GameObjects gameObjects, GameActionContext context, DigitalObject object) {
        final OverpowerCardManager cardManager = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.OVERPOWER_CARD_MANAGER);
        final OverpowerCardBlueprint card = cardManager.getCardBlueprint(gameObjects, object);
        return _cardType.equals(card.getCardType());
    }
}
