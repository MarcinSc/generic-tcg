package com.gempukku.tcg.overpower.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalEnvironment;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.GenericContextObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.gempukku.tcg.overpower.OverpowerContextObjects;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;

import java.util.List;

public class PlaceableOnCardsFilter implements DigitalObjectFilter {
    private StringEvaluator _id;

    public void setId(StringEvaluator id) {
        _id = id;
    }

    @Override
    public boolean accept(GameObjects gameObjects, GameActionContext context, DigitalObject possibleCardToPlaceOn) {
        final OverpowerCardManager overpowerCardManager = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.OVERPOWER_CARD_MANAGER);
        final OverpowerCardBlueprint possibleCharacterToPlaceOn = overpowerCardManager.getCardBlueprint(gameObjects, possibleCardToPlaceOn);
        if (!possibleCharacterToPlaceOn.getCardType().equals("character"))
            return false;

        final DigitalEnvironment digitalEnvironment = GenericContextObjects.extractGameObject(gameObjects, GenericContextObjects.DIGITAL_ENVIRONMENT);
        final PlayerDigitalObjectStackManager inPlay = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.IN_PLAY_ZONE);

        String cardToPlaceId = _id.getValue(gameObjects, context);
        final DigitalObject cardToPlaceObject = digitalEnvironment.getObjectById(cardToPlaceId);
        final OverpowerCardBlueprint cardToPlace = overpowerCardManager.getCardBlueprint(gameObjects, cardToPlaceObject);
        if (!cardToPlace.getPlaceOnFilter().accept(gameObjects, context, possibleCardToPlaceOn))
            return false;

        final List<DigitalObject> placedOn = DigitalObjectUtils.filter(gameObjects, new PlacedOnFilter(new ConstantStringEvaluator(possibleCardToPlaceOn.getId())), context, inPlay.getDigitalObjectsInStack(gameObjects, possibleCardToPlaceOn.getAttributes().get("owner")));

        for (DigitalObject cardPlacedOnCharacter : placedOn) {
            // Check if the card already has card of this type placed
            if (overpowerCardManager.getCardBlueprint(gameObjects, cardPlacedOnCharacter).getCardType().equals(cardToPlace.getCardType()))
                return false;
        }

        return true;
    }
}
