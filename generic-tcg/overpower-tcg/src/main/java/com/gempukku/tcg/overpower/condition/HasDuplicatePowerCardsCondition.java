package com.gempukku.tcg.overpower.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.condition.ActionCondition;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.stack.PlayerDigitalObjectStackManager;
import com.gempukku.tcg.overpower.OverpowerContextObjects;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class HasDuplicatePowerCardsCondition implements ActionCondition {
    private StringEvaluator _player;

    public void setPlayer(StringEvaluator player) {
        _player = player;
    }

    @Override
    public boolean isMet(GameObjects gameObjects, GameActionContext context) {
        final OverpowerCardManager overpowerCardManager = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.OVERPOWER_CARD_MANAGER);

        final String player = _player.getValue(gameObjects, context);
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

        for (Map.Entry<Integer, Collection<String>> cardsByPower :powerCardsByPower.asMap().entrySet()){
            if (cardsByPower.getValue().size()>1) {
                return true;
            }
        }

        return false;
    }
}
