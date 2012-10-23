package com.gempukku.tcg.generic.conditions;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.Condition;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

public class AndCondition implements Condition {
    private List<Condition> _conditions;

    @Required
    public void setConditions(List<Condition> conditions) {
        _conditions = conditions;
    }

    @Override
    public boolean isTrue(GameState gameState) {
        for (Condition condition : _conditions) {
            if (!condition.isTrue(gameState))
                return false;
        }
        return true;
    }
}
