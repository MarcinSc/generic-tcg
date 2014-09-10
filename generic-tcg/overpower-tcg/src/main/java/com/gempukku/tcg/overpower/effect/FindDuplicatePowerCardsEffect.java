package com.gempukku.tcg.overpower.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.effect.GameEffect;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.Filters;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.gempukku.tcg.overpower.OverpowerContextObjects;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;
import com.gempukku.tcg.overpower.filter.CardTypeFilter;
import com.gempukku.tcg.overpower.filter.IsPlacedOnFilter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FindDuplicatePowerCardsEffect implements GameEffect {
    private StringEvaluator _player;
    private StringEvaluator _attributeName;

    public void setAttributeName(StringEvaluator attributeName) {
        _attributeName = attributeName;
    }

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameEffectContext context) {
        final OverpowerCardManager overpowerCardManager = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.OVERPOWER_CARD_MANAGER);
        final PlayerDigitalObjectStackManager inPlayZone = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.IN_PLAY_ZONE);

        final String player = _player.getValue(gameObjects, context);

        final List<DigitalObject> powerCardsInPlay = DigitalObjectUtils.filter(gameObjects, Filters.and(new IsPlacedOnFilter(), new CardTypeFilter("power")), context, inPlayZone.getDigitalObjectsInStack(gameObjects, player));
        Set<Integer> placedPowerCardsPowers = new HashSet<Integer>();
        for (DigitalObject powerCardInPlay : powerCardsInPlay) {
            final OverpowerCardBlueprint cardBlueprint = overpowerCardManager.getCardBlueprint(gameObjects, powerCardInPlay);
            int maxValue = Math.max(Math.max(cardBlueprint.getEnergy(), cardBlueprint.getFighting()), cardBlueprint.getStrength());
            placedPowerCardsPowers.add(maxValue);
        }

        final PlayerDigitalObjectStackManager stack = (PlayerDigitalObjectStackManager) gameObjects.getGameObject("handZone");
        final List<DigitalObject> objects = stack.getDigitalObjectsInStack(gameObjects, player);

        Multimap<Integer, String> powerCardsByPower = HashMultimap.create();
        for (DigitalObject card : objects) {
            final OverpowerCardBlueprint cardBlueprint = overpowerCardManager.getCardBlueprint(gameObjects, card);
            if (cardBlueprint.getCardType().equals("power")) {
                int maxValue = Math.max(Math.max(cardBlueprint.getEnergy(), cardBlueprint.getFighting()), cardBlueprint.getStrength());
                powerCardsByPower.put(maxValue, card.getId());
            }
        }

        Set<String> duplicateCards = new HashSet<String>();
        for (Map.Entry<Integer, Collection<String>> cardsByPower : powerCardsByPower.asMap().entrySet()) {
            if (cardsByPower.getValue().size() > 1 || placedPowerCardsPowers.contains(cardsByPower.getKey())) {
                duplicateCards.addAll(cardsByPower.getValue());
            }
        }

        if (duplicateCards.size()>0) {
            final String attributeName = _attributeName.getValue(gameObjects, context);
            context.setAttribute(attributeName, StringUtils.join(duplicateCards, ","));
        }

        return Result.pass();
    }
}
