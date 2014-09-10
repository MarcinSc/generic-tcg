package com.gempukku.tcg.overpower.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.generic.filter.Filters;
import com.gempukku.tcg.generic.filter.PredicateFilter;
import com.gempukku.tcg.generic.predicate.OwnerPredicate;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.gempukku.tcg.generic.zone.player.DigitalObjectZoneManager;
import com.gempukku.tcg.overpower.OverpowerContextObjects;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;

import java.util.List;

public class PlaceableCardsFilter implements DigitalObjectFilter {
    @Override
    public boolean accept(GameObjects gameObjects, GameEffectContext context, DigitalObject object) {
        final OverpowerCardManager overpowerCardManager = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.OVERPOWER_CARD_MANAGER);
        final OverpowerCardBlueprint possibleCardToPlace = overpowerCardManager.getCardBlueprint(gameObjects, object);
        if (!possibleCardToPlace.getCardType().equals("power"))
            return false;

        final DigitalObjectZoneManager inPlay = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.IN_PLAY_ZONE);

        PlaceableOnCardsFilter placeableOnCardsFilter = new PlaceableOnCardsFilter();
        placeableOnCardsFilter.setId(new ConstantStringEvaluator(object.getId()));
        final List<DigitalObject> cardsInPlay = inPlay.getDigitalObjectsInZone(gameObjects, null);
        for (DigitalObject cardInPlayObj : DigitalObjectUtils.filter(gameObjects, new PredicateFilter(new OwnerPredicate(object.getAttributes().get("owner"))), context, cardsInPlay)) {
            if (placeableOnCardsFilter.accept(gameObjects, context, cardInPlayObj))
                return true;
        }

        return false;
    }
}
