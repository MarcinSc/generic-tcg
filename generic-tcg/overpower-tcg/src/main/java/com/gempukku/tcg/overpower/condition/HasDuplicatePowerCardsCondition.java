package com.gempukku.tcg.overpower.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.effect.GameEffectContext;
import com.gempukku.tcg.generic.condition.ActionCondition;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.Filters;
import com.gempukku.tcg.generic.zone.PlayerDigitalObjectZoneManager;
import com.gempukku.tcg.generic.util.DigitalObjectUtils;
import com.gempukku.tcg.overpower.OverpowerContextObjects;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;
import com.gempukku.tcg.overpower.filter.CardTypeFilter;
import com.gempukku.tcg.overpower.filter.IsPlacedOnFilter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HasDuplicatePowerCardsCondition implements ActionCondition {
    private StringEvaluator _player;

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    @Override
    public boolean isMet(GameObjects gameObjects, GameEffectContext context) {
        final OverpowerCardManager overpowerCardManager = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.OVERPOWER_CARD_MANAGER);
        final PlayerDigitalObjectZoneManager inPlayZone = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.IN_PLAY_ZONE);

        final String player = _player.getValue(gameObjects, context);

        final List<DigitalObject> powerCardsInPlay = DigitalObjectUtils.filter(gameObjects, Filters.and(new IsPlacedOnFilter(), new CardTypeFilter("power")), context, inPlayZone.getDigitalObjectsInZone(gameObjects, player));
        Set<Integer> placedPowerCardsPowers = new HashSet<Integer>();
        for (DigitalObject powerCardInPlay : powerCardsInPlay) {
            final OverpowerCardBlueprint cardBlueprint = overpowerCardManager.getCardBlueprint(gameObjects, powerCardInPlay);
            int maxValue = Math.max(Math.max(cardBlueprint.getEnergy(), cardBlueprint.getFighting()), cardBlueprint.getStrength());
            placedPowerCardsPowers.add(maxValue);
        }

        final PlayerDigitalObjectZoneManager zone = (PlayerDigitalObjectZoneManager) gameObjects.getGameObject("handZone");
        final List<DigitalObject> objects = zone.getDigitalObjectsInZone(gameObjects, player);

        Multimap<Integer, String> powerCardsByPower = HashMultimap.create();
        for (DigitalObject card : objects) {
            final OverpowerCardBlueprint cardBlueprint = overpowerCardManager.getCardBlueprint(gameObjects, card);
            if (cardBlueprint.getCardType().equals("power")) {
                int maxValue = Math.max(Math.max(cardBlueprint.getEnergy(), cardBlueprint.getFighting()), cardBlueprint.getStrength());
                powerCardsByPower.put(maxValue, card.getId());
            }
        }

        for (Map.Entry<Integer, Collection<String>> cardsByPower : powerCardsByPower.asMap().entrySet()) {
            if (cardsByPower.getValue().size() > 1 || placedPowerCardsPowers.contains(cardsByPower.getKey())) {
                return true;
            }
        }

        return false;
    }
}
