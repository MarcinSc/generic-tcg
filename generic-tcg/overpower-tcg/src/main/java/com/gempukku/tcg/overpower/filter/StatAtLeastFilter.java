package com.gempukku.tcg.overpower.filter;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.evaluator.ConstantIntEvaluator;
import com.gempukku.tcg.generic.evaluator.ConstantStringEvaluator;
import com.gempukku.tcg.generic.evaluator.IntEvaluator;
import com.gempukku.tcg.generic.evaluator.StringEvaluator;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;
import com.gempukku.tcg.overpower.OverpowerContextObjects;
import com.gempukku.tcg.overpower.card.OverpowerCardBlueprint;
import com.gempukku.tcg.overpower.card.OverpowerCardManager;

public class StatAtLeastFilter implements DigitalObjectFilter {
    private StringEvaluator _stat;
    private IntEvaluator _value;

    public StatAtLeastFilter() {
    }

    public StatAtLeastFilter(String stat, int value) {
        _stat = new ConstantStringEvaluator(stat);
        _value = new ConstantIntEvaluator(value);
    }

    public void setStat(StringEvaluator stat) {
        _stat = stat;
    }

    public void setValue(IntEvaluator value) {
        _value = value;
    }

    @Override
    public boolean accept(GameObjects gameObjects, GameActionContext context, DigitalObject object) {
        OverpowerCardManager cardManager = OverpowerContextObjects.extractGameObject(gameObjects, OverpowerContextObjects.OVERPOWER_CARD_MANAGER);
        OverpowerCardBlueprint cardBlueprint = cardManager.getCardBlueprint(gameObjects, object);

        String stat = _stat.getValue(gameObjects, context);
        int value = _value.getValue(gameObjects, context);
        if (stat.equals("energy"))
            return cardBlueprint.getEnergy() >= value;
        else if (stat.equals("fighting"))
            return cardBlueprint.getFighting() >= value;
        else if (stat.equals("strength"))
            return cardBlueprint.getStrength() >= value;

        return false;
    }
}
